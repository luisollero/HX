package com.hx.persistence.usage;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;
import static junit.framework.Assert.fail;

import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import org.hibernate.Session;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.junit.Test;

public class OneToManyWithoutAttributesCompositionUnidirectional extends UsageTest {

    @Entity
    static class A {

        @Id
        int id;

        @Basic(optional = false)
        String name;

        @OneToMany()
        @Cascade( { CascadeType.SAVE_UPDATE, CascadeType.DELETE_ORPHAN })
        Set<B> bs = Collections.synchronizedSet(new HashSet<B>());

        @Override
        public int hashCode() {
            final int prime = 31;
            int result = 1;
            result = prime * result + this.id;
            return result;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj)
                return true;
            if (obj == null)
                return false;
            if (getClass() != obj.getClass())
                return false;
            A other = (A) obj;
            if (this.id != other.id)
                return false;
            return true;
        }
    }

    @Entity
    static class B {

        @Id
        int id;

        @Basic(optional = false)
        String name;

        @Override
        public int hashCode() {
            final int prime = 31;
            int result = 1;
            result = prime * result + this.id;
            return result;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj)
                return true;
            if (obj == null)
                return false;
            if (getClass() != obj.getClass())
                return false;
            A other = (A) obj;
            if (this.id != other.id)
                return false;
            return true;
        }
    }

    @Override
    protected Class<?>[] getClasses() {
        return new Class<?>[] { A.class, B.class, };
    }

    @Test
    public void testSave() throws Exception {

        final A a0 = new A();
        a0.id = 1;
        a0.name = "a0";
        execute(new Transaction<A>() {

            @Override
            public A run(Session session) {
                session.save(a0);
                return null;
            }
        });
        execute(new Transaction<A>() {

            @Override
            public A run(Session session) {
                assertEquals(0, ((A) session.get(A.class, 1)).bs.size());
                assertEquals(0, session.createCriteria(B.class).list().size());
                return null;
            }
        });

        final A a1 = new A();
        a1.id = 2;
        a1.name = "a1";
        B b1 = new B();
        b1.id = 1;
        b1.name = "b1";
        a1.bs.add(b1);
        execute(new Transaction<A>() {

            @Override
            public A run(Session session) {
                session.save(a1);
                return null;
            }
        });
        execute(new Transaction<A>() {

            @Override
            public A run(Session session) {
                assertEquals(1, ((A) session.get(A.class, 2)).bs.size());
                assertEquals(1, session.createCriteria(B.class).list().size());
                return null;
            }
        });

        final A a2 = new A();
        a2.id = 3;
        a2.name = "a2";
        B b2 = new B();
        b2.id = 2;
        b2.name = "b2";
        a2.bs.add(b2);
        B b3 = new B();
        b3.id = 3;
        b3.name = "b3";
        a2.bs.add(b3);
        execute(new Transaction<A>() {

            @Override
            public A run(Session session) {
                session.save(a2);
                return null;
            }
        });
        execute(new Transaction<A>() {

            @Override
            public A run(Session session) {
                assertEquals(2, ((A) session.get(A.class, 3)).bs.size());
                assertEquals(3, session.createCriteria(B.class).list().size());
                return null;
            }
        });

        execute(new Transaction<A>() {

            @Override
            public A run(Session session) {

                assertEquals(3, session.createCriteria(A.class).list().size());

                int id = 1;
                assertEquals(id, ((A) session.get(A.class, id)).id);
                assertEquals("a0", ((A) session.get(A.class, id)).name);
                assertEquals(0, ((A) session.get(A.class, id)).bs.size());

                id = 2;
                assertEquals(id, ((A) session.get(A.class, id)).id);
                assertEquals("a1", ((A) session.get(A.class, id)).name);
                assertEquals(1, ((A) session.get(A.class, id)).bs.size());
                assertEquals(1, ((A) session.get(A.class, id)).bs.iterator().next().id);
                assertEquals("b1", ((A) session.get(A.class, id)).bs.iterator().next().name);

                id = 3;
                assertEquals(id, ((A) session.get(A.class, id)).id);
                assertEquals("a2", ((A) session.get(A.class, id)).name);
                assertEquals(2, ((A) session.get(A.class, id)).bs.size());
                Iterator<B> it = ((A) session.get(A.class, id)).bs.iterator();
                boolean found2 = false;
                boolean found3 = false;
                while (it.hasNext()) {
                    B b = it.next();
                    if (b.name.equals("b2")) {
                        found2 = true;
                    } else if (b.name.equals("b3")) {
                        found3 = true;
                    } else {
                        fail();
                    }
                }
                assertTrue(found2);
                assertTrue(found3);

                return null;
            }
        });
    }

    @Test
    public void testUpdate() throws Exception {

        final A a1 = new A();
        a1.id = 1;
        a1.name = "a1";
        B b1 = new B();
        b1.id = 1;
        b1.name = "b1";
        a1.bs.add(b1);
        final B b2 = new B();
        b2.id = 2;
        b2.name = "b2";
        a1.bs.add(b2);
        execute(new Transaction<A>() {

            @Override
            public A run(Session session) {
                session.save(a1);
                return null;
            }
        });
        execute(new Transaction<A>() {

            @Override
            public A run(Session session) {
                assertEquals(2, ((A) session.get(A.class, 1)).bs.size());
                assertEquals(2, session.createCriteria(B.class).list().size());
                return null;
            }
        });

        b2.name = "b2 name changed";
        final B b3 = new B();
        b3.id = 3;
        b3.name = "b3";
        a1.bs.remove(b1);
        a1.bs.add(b3);
        execute(new Transaction<A>() {

            @Override
            public A run(Session session) {
                session.update(a1);
                return null;
            }
        });
        execute(new Transaction<A>() {

            @Override
            public A run(Session session) {

                assertEquals(2, ((A) session.get(A.class, 1)).bs.size());
                {
                    Iterator<B> it = ((A) session.get(A.class, 1)).bs.iterator();
                    boolean found2 = false;
                    boolean found3 = false;
                    while (it.hasNext()) {
                        B b = it.next();
                        if (b.id == b2.id && b.name.equals("b2 name changed")) {
                            found2 = true;
                        } else if (b.id == b3.id && b.name.equals("b3")) {
                            found3 = true;
                        } else {
                            fail();
                        }
                    }
                    assertTrue(found2);
                    assertTrue(found3);
                }

                @SuppressWarnings("unchecked")
                List<B> bs = session.createCriteria(B.class).list();
                assertEquals(2, bs.size());
                {
                    Iterator<B> it = bs.iterator();
                    boolean found2 = false;
                    boolean found3 = false;
                    while (it.hasNext()) {
                        B b = it.next();
                        if (b.id == b2.id && b.name.equals("b2 name changed")) {
                            found2 = true;
                        } else if (b.id == b3.id && b.name.equals("b3")) {
                            found3 = true;
                        } else {
                            fail();
                        }
                    }
                    assertTrue(found2);
                    assertTrue(found3);
                }

                return null;
            }
        });
    }

    @Test
    public void testUpdateNotDetached() throws Exception {

        execute(new Transaction<A>() {

            @Override
            public A run(Session session) {
                final A a1 = new A();
                a1.id = 1;
                a1.name = "a1";
                B b1 = new B();
                b1.id = 1;
                b1.name = "b1";
                a1.bs.add(b1);
                final B b2 = new B();
                b2.id = 2;
                b2.name = "b2";
                a1.bs.add(b2);
                session.save(a1);
                return null;
            }
        });
        execute(new Transaction<A>() {

            @Override
            public A run(Session session) {
                assertEquals(2, ((A) session.get(A.class, 1)).bs.size());
                assertEquals(2, session.createCriteria(B.class).list().size());
                return null;
            }
        });

        execute(new Transaction<A>() {

            @Override
            public A run(Session session) {
                A a1 = (A) session.get(A.class, 1);
                B b1 = (B) session.get(B.class, 1);
                B b2 = (B) session.get(B.class, 2);
                b2.name = "b2 name changed";
                final B b3 = new B();
                b3.id = 3;
                b3.name = "b3";
                a1.bs.remove(b1);
                a1.bs.add(b3);
                session.update(a1);
                return null;
            }
        });
        execute(new Transaction<A>() {

            @Override
            public A run(Session session) {

                assertEquals(2, ((A) session.get(A.class, 1)).bs.size());
                {
                    Iterator<B> it = ((A) session.get(A.class, 1)).bs.iterator();
                    boolean found2 = false;
                    boolean found3 = false;
                    while (it.hasNext()) {
                        B b = it.next();
                        if (b.id == 2 && b.name.equals("b2 name changed")) {
                            found2 = true;
                        } else if (b.id == 3 && b.name.equals("b3")) {
                            found3 = true;
                        } else {
                            fail();
                        }
                    }
                    assertTrue(found2);
                    assertTrue(found3);
                }

                @SuppressWarnings("unchecked")
                List<B> bs = session.createCriteria(B.class).list();
                assertEquals(2, bs.size());
                {
                    Iterator<B> it = bs.iterator();
                    boolean found2 = false;
                    boolean found3 = false;
                    while (it.hasNext()) {
                        B b = it.next();
                        if (b.id == 2 && b.name.equals("b2 name changed")) {
                            found2 = true;
                        } else if (b.id == 3 && b.name.equals("b3")) {
                            found3 = true;
                        } else {
                            fail();
                        }
                    }
                    assertTrue(found2);
                    assertTrue(found3);
                }

                return null;
            }
        });
    }

    @Test
    public void testDelete() throws Exception {

        final A a1 = new A();
        a1.id = 1;
        a1.name = "a1";
        B b1 = new B();
        b1.id = 1;
        b1.name = "b1";
        a1.bs.add(b1);
        final B b2 = new B();
        b2.id = 2;
        b2.name = "b2";
        a1.bs.add(b2);
        execute(new Transaction<A>() {

            @Override
            public A run(Session session) {
                session.save(a1);
                return null;
            }
        });
        execute(new Transaction<A>() {

            @Override
            public A run(Session session) {
                assertEquals(1, session.createCriteria(A.class).list().size());
                assertEquals(2, session.createCriteria(B.class).list().size());
                assertEquals(2, ((A) session.get(A.class, 1)).bs.size());
                return null;
            }
        });

        execute(new Transaction<A>() {

            @Override
            public A run(Session session) {
                session.delete(a1);
                return null;
            }
        });
        execute(new Transaction<A>() {

            @Override
            public A run(Session session) {
                assertEquals(0, session.createCriteria(A.class).list().size());
                assertEquals(0, session.createCriteria(B.class).list().size());
                return null;
            }
        });
    }

    @Test
    public void testDeleteWithClear() throws Exception {

        final A a1 = new A();
        a1.id = 1;
        a1.name = "a1";
        B b1 = new B();
        b1.id = 1;
        b1.name = "b1";
        a1.bs.add(b1);
        final B b2 = new B();
        b2.id = 2;
        b2.name = "b2";
        a1.bs.add(b2);
        execute(new Transaction<A>() {

            @Override
            public A run(Session session) {
                session.save(a1);
                return null;
            }
        });
        execute(new Transaction<A>() {

            @Override
            public A run(Session session) {
                assertEquals(1, session.createCriteria(A.class).list().size());
                assertEquals(2, session.createCriteria(B.class).list().size());
                assertEquals(2, ((A) session.get(A.class, 1)).bs.size());
                return null;
            }
        });

        a1.bs.clear();
        execute(new Transaction<A>() {

            @Override
            public A run(Session session) {
                session.delete(a1);
                return null;
            }
        });
        execute(new Transaction<A>() {

            @Override
            public A run(Session session) {
                assertEquals(0, session.createCriteria(A.class).list().size());
                assertEquals(0, session.createCriteria(B.class).list().size());
                return null;
            }
        });
    }
}

package com.hx.persistence.usage;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.fail;

import java.io.Serializable;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.hibernate.Session;
import org.hibernate.exception.GenericJDBCException;
import org.junit.Test;

public class ManyToManyWithAttributesBidirectional extends UsageTest {

    @Entity
    static class A {

        @Id
        int id;

        @Basic(optional = false)
        String name;

        @OneToMany(mappedBy = "pk.a")
        Set<AB> abs = Collections.synchronizedSet(new HashSet<AB>());

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
            final A other = (A) obj;
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

        @OneToMany(mappedBy = "pk.b")
        Set<AB> abs = Collections.synchronizedSet(new HashSet<AB>());

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
            final B other = (B) obj;
            if (this.id != other.id)
                return false;
            return true;
        }
    }

    @Entity
    static class AB {

        @EmbeddedId
        ABPK pk;

        @Basic(optional = false)
        int value;

        @Override
        public int hashCode() {
            final int prime = 31;
            int result = 1;
            result = prime * result + ((this.pk == null) ? 0 : this.pk.hashCode());
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
            final AB other = (AB) obj;
            if (this.pk == null) {
                if (other.pk != null)
                    return false;
            } else if (!this.pk.equals(other.pk))
                return false;
            return true;
        }
    }

    @Embeddable
    static class ABPK implements Serializable {

        private static final long serialVersionUID = 1L;

        @ManyToOne(optional = false)
        A a;

        @ManyToOne(optional = false)
        B b;

        @Override
        public int hashCode() {
            final int prime = 31;
            int result = 1;
            result = prime * result + new Integer(a.id).hashCode();
            result = prime * result + new Integer(b.id).hashCode();
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
            final ABPK other = (ABPK) obj;
            if (a == null) {
                if (other.a != null)
                    return false;
            } else if (!a.equals(other.a))
                return false;
            if (b == null) {
                if (other.b != null)
                    return false;
            } else if (!b.equals(other.b))
                return false;
            return true;
        }
    }

    @Override
    protected Class<?>[] getClasses() {
        return new Class<?>[] { A.class, B.class, AB.class, };
    }

    @Test
    public void testSave() throws Exception {

        final A a1 = new A();
        a1.id = 1;
        a1.name = "a1";
        final A a2 = new A();
        a2.id = 2;
        a2.name = "a2";
        final B b1 = new B();
        b1.id = 1;
        b1.name = "b1";
        final B b2 = new B();
        b2.id = 2;
        b2.name = "b2";
        execute(new Transaction<A>() {

            @Override
            public A run(Session session) {
                session.save(a1);
                session.save(a2);
                session.save(b1);
                session.save(b2);
                return null;
            }
        });

        final AB ab1 = new AB();
        ab1.pk = new ABPK();
        ab1.pk.a = a1;
        ab1.pk.b = b1;
        ab1.value = 1;
        final AB ab2 = new AB();
        ab2.pk = new ABPK();
        ab2.pk.a = a1;
        ab2.pk.b = b2;
        ab2.value = 1;
        final AB ab3 = new AB();
        ab3.pk = new ABPK();
        ab3.pk.a = a2;
        ab3.pk.b = b2;
        ab3.value = 1;
        execute(new Transaction<A>() {

            @Override
            public A run(Session session) {
                session.save(ab1);
                session.save(ab2);
                session.save(ab3);
                return null;
            }
        });
        execute(new Transaction<A>() {

            @Override
            public A run(Session session) {
                assertEquals(2, session.createCriteria(A.class).list().size());
                assertEquals(2, session.createCriteria(B.class).list().size());
                assertEquals(3, session.createCriteria(AB.class).list().size());
                assertEquals(2, ((A) session.get(A.class, 1)).abs.size());
                assertEquals(1, ((A) session.get(A.class, 2)).abs.size());

                return null;
            }
        });
    }

    @Test
    public void testUpdate() throws Exception {

        final A a1 = new A();
        a1.id = 1;
        a1.name = "a1";
        final A a2 = new A();
        a2.id = 2;
        a2.name = "a2";
        final B b1 = new B();
        b1.id = 1;
        b1.name = "b1";
        final B b2 = new B();
        b2.id = 2;
        b2.name = "b2";
        execute(new Transaction<A>() {

            @Override
            public A run(Session session) {
                session.save(a1);
                session.save(a2);
                session.save(b1);
                session.save(b2);
                return null;
            }
        });

        final AB ab1 = new AB();
        ab1.pk = new ABPK();
        ab1.pk.a = a1;
        ab1.pk.b = b1;
        ab1.value = 1;
        final AB ab2 = new AB();
        ab2.pk = new ABPK();
        ab2.pk.a = a1;
        ab2.pk.b = b2;
        ab2.value = 1;
        final AB ab3 = new AB();
        ab3.pk = new ABPK();
        ab3.pk.a = a2;
        ab3.pk.b = b2;
        ab3.value = 1;
        execute(new Transaction<A>() {

            @Override
            public A run(Session session) {
                session.save(ab1);
                session.save(ab2);
                session.save(ab3);
                return null;
            }
        });
        execute(new Transaction<A>() {

            @Override
            public A run(Session session) {
                assertEquals(2, session.createCriteria(A.class).list().size());
                assertEquals(2, session.createCriteria(B.class).list().size());
                assertEquals(3, session.createCriteria(AB.class).list().size());
                assertEquals(2, ((A) session.get(A.class, 1)).abs.size());
                assertEquals(1, ((A) session.get(A.class, 2)).abs.size());

                return null;
            }
        });

        execute(new Transaction<A>() {

            @Override
            public A run(Session session) {
                session.delete(ab3);
                return null;
            }
        });
        execute(new Transaction<A>() {

            @Override
            public A run(Session session) {
                assertEquals(2, session.createCriteria(A.class).list().size());
                assertEquals(2, session.createCriteria(B.class).list().size());
                assertEquals(2, session.createCriteria(AB.class).list().size());
                assertEquals(2, ((A) session.get(A.class, 1)).abs.size());
                assertEquals(0, ((A) session.get(A.class, 2)).abs.size());

                return null;
            }
        });
    }

    @Test
    public void testDelete() throws Exception {

        final A a1 = new A();
        a1.id = 1;
        a1.name = "a1";
        final A a2 = new A();
        a2.id = 2;
        a2.name = "a2";
        final B b1 = new B();
        b1.id = 1;
        b1.name = "b1";
        final B b2 = new B();
        b2.id = 2;
        b2.name = "b2";
        execute(new Transaction<A>() {

            @Override
            public A run(Session session) {
                session.save(a1);
                session.save(a2);
                session.save(b1);
                session.save(b2);
                return null;
            }
        });

        final AB ab1 = new AB();
        ab1.pk = new ABPK();
        ab1.pk.a = a1;
        ab1.pk.b = b1;
        ab1.value = 1;
        final AB ab2 = new AB();
        ab2.pk = new ABPK();
        ab2.pk.a = a1;
        ab2.pk.b = b2;
        ab2.value = 1;
        final AB ab3 = new AB();
        ab3.pk = new ABPK();
        ab3.pk.a = a2;
        ab3.pk.b = b2;
        ab3.value = 1;
        execute(new Transaction<A>() {

            @Override
            public A run(Session session) {
                session.save(ab1);
                session.save(ab2);
                session.save(ab3);
                return null;
            }
        });
        execute(new Transaction<A>() {

            @Override
            public A run(Session session) {
                assertEquals(2, session.createCriteria(A.class).list().size());
                assertEquals(2, session.createCriteria(B.class).list().size());
                assertEquals(3, session.createCriteria(AB.class).list().size());
                assertEquals(2, ((A) session.get(A.class, 1)).abs.size());
                assertEquals(1, ((A) session.get(A.class, 2)).abs.size());

                return null;
            }
        });

        try {
            execute(new Transaction<A>() {

                @Override
                public A run(Session session) {
                    session.delete(a1);
                    return null;
                }
            });
            fail();
        } catch (GenericJDBCException e) {
            assertEquals("Could not execute JDBC batch update", e.getMessage());
        }

        try {
            execute(new Transaction<A>() {

                @Override
                public A run(Session session) {
                    session.delete(session.get(A.class, 1));
                    return null;
                }
            });
            fail();
        } catch (GenericJDBCException e) {
            assertEquals("Could not execute JDBC batch update", e.getMessage());
        }

        try {
            execute(new Transaction<A>() {

                @Override
                public A run(Session session) {
                    session.delete(session.get(A.class, 2));
                    return null;
                }
            });
            fail();
        } catch (GenericJDBCException e) {
            assertEquals("Could not execute JDBC batch update", e.getMessage());
        }

        execute(new Transaction<A>() {

            @Override
            public A run(Session session) {
                session.delete(ab3);
                session.delete(session.get(A.class, 2));
                return null;
            }
        });
        execute(new Transaction<A>() {

            @Override
            public A run(Session session) {
                assertEquals(1, session.createCriteria(A.class).list().size());
                assertEquals(2, session.createCriteria(B.class).list().size());
                assertEquals(2, session.createCriteria(AB.class).list().size());
                assertEquals(2, ((A) session.get(A.class, 1)).abs.size());

                return null;
            }
        });
    }
}

package com.hx.persistence.usage;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.fail;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import junit.framework.Assert;

import org.hibernate.Session;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.exception.GenericJDBCException;
import org.junit.Test;

public class OneToOneTest extends UsageTest {

    @Entity
    static class A {

        @Id
        int id;

        A() {
        }

        A(int id) {
            this.id = id;
        }

        @Basic(optional = false)
        String name;

        @OneToOne(mappedBy = "a")
        @Cascade( { CascadeType.ALL, CascadeType.DELETE_ORPHAN })
        B b;

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

        B() {
        }

        B(int id) {
            this.id = id;
        }

        @Basic(optional = false)
        String name;

        @OneToOne()
        @Cascade( { CascadeType.ALL, CascadeType.DELETE_ORPHAN })
        A a;

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

    @Override
    protected Class<?>[] getClasses() {
        return new Class<?>[] { A.class, B.class, };
    }

    @Test
    public void testSaveA() throws Exception {

        final A a = execute(new Transaction<A>() {

            @Override
            public A run(Session session) {
                A a = new A(1);
                a.name = "name" + a.id;
                session.save(a);
                return a;
            }
        });
        @SuppressWarnings("unused")
        final B b = execute(new Transaction<B>() {

            @Override
            public B run(Session session) {
                B b = new B(2);
                b.name = "name" + b.id;
                b.a = a;
                session.save(b);
                return b;
            }
        });
        execute(new Transaction<B>() {

            @Override
            public B run(Session session) {
                A a = (A) session.get(A.class, 1);
                B b = (B) session.get(B.class, 2);
                Assert.assertSame(a, b.a);
                Assert.assertSame(b, a.b);
                return null;
            }
        });
    }

    @Test
    public void testSaveB() throws Exception {

        final B b = execute(new Transaction<B>() {

            @Override
            public B run(Session session) {
                B b = new B(2);
                b.name = "name" + b.id;
                session.save(b);
                return b;
            }
        });
        @SuppressWarnings("unused")
        final A a = execute(new Transaction<A>() {

            @Override
            public A run(Session session) {
                A a = new A(1);
                a.name = "name" + a.id;
                a.b = b;
                session.save(a);
                return a;
            }
        });
        execute(new Transaction<B>() {

            @Override
            public B run(Session session) {
                A a = (A) session.get(A.class, 1);
                B b = (B) session.get(B.class, 2);
                Assert.assertNull(b.a);
                Assert.assertNull(a.b);
                return null;
            }
        });
    }

    @Test
    public void testDeleteA() throws Exception {

        final A a = execute(new Transaction<A>() {

            @Override
            public A run(Session session) {
                A a = new A(1);
                a.name = "name" + a.id;
                session.save(a);
                return a;
            }
        });
        @SuppressWarnings("unused")
        final B b = execute(new Transaction<B>() {

            @Override
            public B run(Session session) {
                B b = new B(2);
                b.name = "name" + b.id;
                b.a = a;
                session.save(b);
                return b;
            }
        });
        execute(new Transaction<B>() {

            @Override
            public B run(Session session) {
                A a = (A) session.get(A.class, 1);
                B b = (B) session.get(B.class, 2);
                Assert.assertSame(a, b.a);
                Assert.assertSame(b, a.b);
                return null;
            }
        });

        try {
            execute(new Transaction<B>() {

                @Override
                public B run(Session session) {
                    session.delete(a);
                    return null;
                }
            });
            fail();
        } catch (GenericJDBCException e) {
            assertEquals("Could not execute JDBC batch update", e.getMessage());
        }
        final A a2 = execute(new Transaction<A>() {

            @Override
            public A run(Session session) {
                A a = (A) session.get(A.class, 1);
                return a;
            }
        });
        execute(new Transaction<B>() {

            @Override
            public B run(Session session) {
                session.delete(a2);
                return null;
            }
        });
    }

    @Test
    public void testDeleteB() throws Exception {

        final A a = execute(new Transaction<A>() {

            @Override
            public A run(Session session) {
                A a = new A(1);
                a.name = "name" + a.id;
                session.save(a);
                return a;
            }
        });
        final B b = execute(new Transaction<B>() {

            @Override
            public B run(Session session) {
                B b = new B(2);
                b.name = "name" + b.id;
                b.a = a;
                session.save(b);
                return b;
            }
        });
        execute(new Transaction<B>() {

            @Override
            public B run(Session session) {
                A a = (A) session.get(A.class, 1);
                B b = (B) session.get(B.class, 2);
                Assert.assertSame(a, b.a);
                Assert.assertSame(b, a.b);
                return null;
            }
        });

        execute(new Transaction<B>() {

            @Override
            public B run(Session session) {
                session.delete(b);
                return null;
            }
        });
        execute(new Transaction<B>() {

            @Override
            public B run(Session session) {
                Assert.assertEquals(0, session.createCriteria(A.class).list().size());
                Assert.assertEquals(0, session.createCriteria(B.class).list().size());
                return null;
            }
        });
    }
}

package ua.ronaldo173.library.web;

import java.io.IOException;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by Developer on 08.04.2016.
 */
public class TestSingleton {

    public static void main(String[] args) throws IOException, ClassNotFoundException {

       A a = new A();



        B b = new B();
//        b.c = 2;


        System.out.println(a.equals(b));
    }

}

class MySingleton implements Serializable {
    private static final long serialVersionUID = -7604766932017737115L;

    private MySingleton() {
    }

    public static MySingleton getInstance() {
        return SingletonHelper.INSTANCE;
    }

    protected Object readResolve() {
        return getInstance();
    }

    private static class SingletonHelper {
        private static final MySingleton INSTANCE = new MySingleton();
    }

}

class Person1 {
    private final Date birthdate = new Date();

    public boolean isInDiapasonBad() {
        Calendar gmtCal = Calendar.getInstance(TimeZone.getTimeZone("GMT"));

        gmtCal.set(1950, Calendar.JANUARY, 0, 0, 0);
        Date start = gmtCal.getTime();
        gmtCal.set(2019, Calendar.JANUARY, 0, 0, 0);
        Date end = gmtCal.getTime();

        return birthdate.compareTo(start) >= 0 &&
                birthdate.compareTo(end) < 0;
    }
}

class Person2 {
    private static final Date start;
    private static final Date end;

    static {
        Calendar gmtCal = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
        gmtCal.set(1950, Calendar.JANUARY, 1, 0, 0);
        start = gmtCal.getTime();
        gmtCal.set(1965, Calendar.JANUARY, 1, 0, 0, 0);
        end = gmtCal.getTime();

    }

    private final Date birthdate = new Date(1955, 1, 1);

    public boolean isInDiapasonGood() {
        return birthdate.compareTo(start) >= 0 &&
                birthdate.compareTo(end) < 0;
    }
}

class A{
    int a;
    int b;
    public A() {
        System.out.println("a constr");
    }

    void a(){
        System.out.println("a");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        A a1 = (A) o;

        if (a != a1.a) return false;
        return b == a1.b;

    }

    @Override
    public int hashCode() {
        int result = a;
        result = 31 * result + b;
        return result;
    }
}
class B extends A{
    int c;

    public B() {
        super();
        System.out.println("b constr");
    }

    @Override
    void a() {
        System.out.println("\nb");
        super.a();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof B)) return false;
        if (!super.equals(o)) return false;

        B b = (B) o;

        return c == b.c;

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + c;
        return result;
    }
}
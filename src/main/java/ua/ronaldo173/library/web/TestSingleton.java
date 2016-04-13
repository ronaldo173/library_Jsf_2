package ua.ronaldo173.library.web;

import java.io.IOException;
import java.io.Serializable;
import java.util.*;

/**
 * Created by Developer on 08.04.2016.
 */
public class TestSingleton {

    public static void main(String[] args) throws IOException, ClassNotFoundException {

        Map<first, Integer> map = new HashMap<>();
        Map<first, Integer> map2 = new HashMap<>();
        int op = 50000;
       long t0 = System.currentTimeMillis();
        for (int i = 0; i < op; i++) {
            map.put(new first(i, (double)i, Integer.toString(i)), i);
        }
        for (int i = 0; i < op; i++) {
            map.get(new first(i, (double)i, Integer.toString(i)));
        }
       long t2 = System.currentTimeMillis();
        System.out.println(t2-t0);

       long t10 = System.currentTimeMillis();
        for (int i = 0; i < op; i++) {
            map2.put(new first(i, (double)i, Integer.toString(i)), i);
        }
        for (int i = 0; i < op; i++) {
            map2.get(new first(i, (double)i, Integer.toString(i)));
        }
       long t12 = System.currentTimeMillis();
        System.out.println(t12-t10);
    }

}

class first{
    int a;
    double b;
    String c;

    public first(int a, double b, String c) {
        this.a = a;
        this.b = b;
        this.c = c;
    }

    @Override
    public int hashCode() {
        return 1;
    }
}

class second{
    int a;
    double b;
    String c;

    public second(int a, double b, String c) {
        this.a = a;
        this.b = b;
        this.c = c;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof second)) return false;

        second second = (second) o;

        if (a != second.a) return false;
        if (Double.compare(second.b, b) != 0) return false;
        return c != null ? c.equals(second.c) : second.c == null;

    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = a;
        temp = Double.doubleToLongBits(b);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (c != null ? c.hashCode() : 0);
        return result;
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
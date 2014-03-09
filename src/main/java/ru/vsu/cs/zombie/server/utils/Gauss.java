package ru.vsu.cs.zombie.server.utils;

public class Gauss {

    boolean ready = false;
    double second = 0.0;

    public double Next(double mean, double dev) {
        if (this.ready) {
            this.ready = false;
            return this.second * dev + mean;
        } else {
            double u, v, s;
            do {
                u = 2.0 * Math.random() - 1.0;
                v = 2.0 * Math.random() - 1.0;
                s = u * u + v * v;
            } while (s > 1.0 || s == 0.0);

            double r = Math.sqrt(-2.0 * Math.log(s) / s);
            this.second = r * u;
            this.ready = true;
            double result = r * v * dev + mean;
            return result - (int)result;
        }
    }
}

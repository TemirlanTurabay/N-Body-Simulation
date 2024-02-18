public class NBody {
    public static void main(String[] args) {
        // Step 1. Parse command-line arguments.
        double tau = Double.parseDouble(args[0]);
        double dt = Double.parseDouble(args[1]);
        // Step 2. Read universe from standard input.
        int n = StdIn.readInt();
        double radius = StdIn.readDouble();
        double[] px = new double[n];
        double[] py = new double[n];
        double[] vx = new double[n];
        double[] vy = new double[n];
        double[] mass = new double[n];
        String[] image = new String[n];
        for (int i = 0; i < n; i++) {
            px[i] = StdIn.readDouble();
            py[i] = StdIn.readDouble();
            vx[i] = StdIn.readDouble();
            vy[i] = StdIn.readDouble();
            mass[i] = StdIn.readDouble();
            image[i] = StdIn.readString();
        }
        // Step 3. Initialize standard drawing.
        StdDraw.setXscale(0.0 - radius, radius);
        StdDraw.setYscale(0.0 - radius, radius);
        StdDraw.enableDoubleBuffering();
        // Step 4. Play music on standard audio.
        StdAudio.play("2001.wav");
        // Step 5. Simulate the universe.
        for (double t = 0.0; t < tau; t += dt) {
            // Step 5A. Calculate net forces.
            double[] fx = new double[n];
            double[] fy = new double[n];
            double[] ax = new double[n];
            double[] ay = new double[n];
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    if (i != j) {
                        double G = 6.67e-11;
                        double dx = px[j] - px[i];
                        double dy = py[j] - py[i];
                        double r = Math.sqrt(
                                Math.pow(dx, 2) + Math.pow(dy, 2));
                        double sin = dy / r;
                        double cos = dx / r;
                        double F = (G * mass[i] * mass[j]) / (Math.pow(r, 2));
                        fx[i] += F * cos;
                        fy[i] += F * sin;
                    }
                }
            }
            // Step 5B. Update velocities and positions.
            for (int i = 0; i < n; i++) {
                ax[i] = fx[i] / mass[i];
                ay[i] = fy[i] / mass[i];
                vx[i] += ax[i] * dt;
                vy[i] += ay[i] * dt;
                px[i] += vx[i] * dt;
                py[i] += vy[i] * dt;
            }
            // Step 5C. Draw universe to standard drawing.
            StdDraw.picture(0.0, 0.0, "starfield.jpg");
            for (int j = 0; j < n; j++) {
                StdDraw.picture(px[j], py[j], image[j]);
            }
            StdDraw.show();
            StdDraw.pause(20);
        }


        // Step 6. Print universe to standard output.
        StdOut.printf("%d\n", n);
        StdOut.printf("%.2e\n", radius);
        for (int i = 0; i < n; i++) {
            StdOut.printf("%11.4e %11.4e %11.4e %11.4e %11.4e %12s\n",
                          px[i], py[i], vx[i], vy[i], mass[i], image[i]);
        }
    }
}

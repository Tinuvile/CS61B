public class NBody {

    @SuppressWarnings("deprecation")
    public static double readRadius(String filename) {
        // 创建文件输入流
        In in = new In(filename);
        // 读取第一个整数
        int num = in.readInt();
        //读取半径
        double radius = in.readDouble();
        return radius;
    }

    @SuppressWarnings("deprecation")
    public static Planet[] readPlanets(String filename) {
        In in = new In(filename);
        int num = in.readInt();
        double radius = in.readDouble();
        Planet[] planets = new Planet[num];

        for (int i = 0; i < num; i++) {
            double xp = in.readDouble();
            double yp = in.readDouble();
            double vx = in.readDouble();
            double vy = in.readDouble();
            double m = in.readDouble();
            String img = in.readString();

            planets[i] = new Planet(xp, yp, vx, vy, m, img);
        }

        return planets;
    }

    public static void main(String[] args) {
        // 解析命令行参数
        double T = Double.parseDouble(args[0]);
        double dt = Double.parseDouble(args[1]);
        String filename = args[2];

        //读取宇宙的半径和行星
        double radius = readRadius(filename);
        Planet[] planets = readPlanets(filename);

        //设置绘图比例
        StdDraw.setXscale(-radius, radius);
        StdDraw.setYscale(-radius, radius);
        StdDraw.enableDoubleBuffering();

        /*
        StdDraw.picture(0,0,"images/starfield.jpg");

        // 绘制所有行星
        for (Planet planet : planets){
            planet.draw();
        }

        StdDraw.show();
         */

        // 创建时间变量
        double time = 0;

        // 主循环，直到time达到T
        while (time <= T) {
            double[] xForces = new double[planets.length];
            double[] yForces = new double[planets.length];

            for (int i=0;i<planets.length;i++){
                xForces[i]=planets[i].calcNetForceExertedByX(planets);
                yForces[i]=planets[i].calcNetForceExertedByY(planets);
            }

            for (int i=0;i<planets.length;i++){
                planets[i].update(dt,xForces[i],yForces[i]);
            }

            StdDraw.picture(0,0,"images/starfield.jpg");

            for (Planet planet:planets){
                planet.draw();
            }

            StdDraw.show();

            StdDraw.pause(10);

            time += dt;
        }

        StdOut.printf("%d\n", planets.length);
        StdOut.printf("%.2e\n", radius);
        for (int i = 0; i < planets.length; i++) {
            StdOut.printf("%11.4e %11.4e %11.4e %11.4e %11.4e %12s\n",
                    planets[i].xxPos, planets[i].yyPos, planets[i].xxVel,
                    planets[i].yyVel, planets[i].mass, planets[i].imgFileName);
        }
    }
}
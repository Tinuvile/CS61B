public class Planet {
    public double xxPos;
    public double yyPos;
    public double xxVel;
    public double yyVel;
    public double mass;
    public String imgFileName;

    private static final double G = 6.674e-11;

    public Planet(double xP, double yP, double xV, double yV, double m, String img) {
        xxPos = xP;
        yyPos = yP;
        xxVel = xV;
        yyVel = yV;
        mass = m;
        imgFileName = img;
    }

    public Planet(Planet p) {
        xxPos = p.xxPos;
        yyPos = p.yyPos;
        xxVel = p.xxVel;
        yyVel = p.yyVel;
        mass = p.mass;
        imgFileName = p.imgFileName;
    }

    // 计算与另一个 Planet 的距离
    public double calcDistance(Planet other) {
        double dx = this.xxPos - other.xxPos;
        double dy = this.yyPos - other.yyPos;
        return Math.sqrt(dx * dx + dy * dy);
    }

    // 计算一个行星对这个行星施加的力
    public double calcForceExertedBy(Planet other) {
        double distance = this.calcDistance(other);
        if (distance == 0) {
            return 0;
        }
        return (G * this.mass * other.mass) / (distance * distance);
    }

    // 计算施加在当前行星上的 X 力
    public double calcForceExertedByX(Planet other) {
        double force = this.calcForceExertedBy(other);
        double dx = other.xxPos - this.xxPos;
        double distance = this.calcDistance(other);
        return force * (dx / distance);
    }

    // 计算施加在当前行星上的 Y 力
    public double calcForceExertedByY(Planet other) {
        double force = this.calcForceExertedBy(other);
        double dy = other.yyPos - this.yyPos;
        double distance = this.calcDistance(other);
        return force * (dy / distance);
    }


    // 计算施加在当前行星上的净 X 力
    public double calcNetForceExertedByX(Planet[] allPlanets) {
        double netForceX = 0.0;
        for (Planet other : allPlanets) {
            // 忽略自身引力
            if (!this.equals(other)) {
                double force = this.calcForceExertedBy(other);
                double dx = other.xxPos - this.xxPos;
                double distance = this.calcDistance(other);
                double forceX = force * (dx / distance);
                netForceX += forceX;
            }
        }
        return netForceX;
    }

    // 计算施加在当前行星上的净 Y 力
    public double calcNetForceExertedByY(Planet[] allPlanets) {
        double netForceY = 0.0;
        for (Planet other : allPlanets) {
            // 忽略自身引力
            if (!this.equals(other)) {
                double force = this.calcForceExertedBy(other);
                double dy = other.yyPos - this.yyPos;
                double distance = this.calcDistance(other);
                double forceY = force * (dy / distance);
                netForceY += forceY;
            }
        }
        return netForceY;
    }

    // 更新行星的位置与速度
    public void update(double dt, double fx, double fy) {
        double aX = fx / mass;
        double aY = fy / mass;

        xxVel += dt * aX;
        yyVel += dt * aY;

        xxPos += dt * xxVel;
        yyPos += dt * yyVel;
    }

    public void draw() {
        StdDraw.picture(xxPos, yyPos, "images/" + imgFileName);
    }
}
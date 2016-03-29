package fka.dartcounter;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;


/**
 * Created by FKA on 21.03.2016.
 */
public class Dartboard extends View {

    int points;
    int clickCounter;
    int clickedPoints;
    ArrayList<Integer> dartPoints = new ArrayList<>(Arrays.asList(6, 10, 15, 2, 17, 3, 19, 7, 16, 8, 11, 14, 9, 12, 5, 20, 1, 18, 4, 13));

    public Dartboard(Context context) {
        super(context);
        this.clickCounter = 0;
        this.clickedPoints = 0;
    }

    //** getters **//
    public int getClickCounter() {
        return clickCounter;
    }

    public ArrayList<Integer> getDartPoints() {
        return dartPoints;
    }

    public int getClickedPoints() {
        return clickedPoints;
    }

    public int getPoints() {
        return points;
    }

    //** setters **//
    public void setClickCounter(int clickCounter) {
        this.clickCounter = clickCounter;
    }

    public void setDartPoints(ArrayList<Integer> dartPoints) {
        this.dartPoints = dartPoints;
    }

    public void setClickedPoints(int clickedPoints) {
        this.clickedPoints = clickedPoints;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawDartBoard(canvas);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        super.onTouchEvent(event);

        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            //
        } else if (event.getAction() == MotionEvent.ACTION_UP) {
            double clickedX = event.getX();
            double clickedY = event.getY();
            int dartPointsHit = getDartPointsHit(clickedX, clickedY);
            points = getDartPointsHit(clickedX, clickedY);
            clickedPoints += dartPointsHit;

            //Toast.makeText(getContext(), dartPointsHit + " points", Toast.LENGTH_SHORT).show();
        }
        return true;
    }


    ///***   methods   ***///
    private double getCenterX() {
        double centerX = (double) getWidth() / 2;
        //System.out.println("centerX: " + centerX);
        return centerX;
    }

    private double getCenterY() {
        double centerY = (double) getHeight() / 2;
        //System.out.println("centerY: " + centerY);
        return centerY;
    }

    private double getRadius() {
        double radius;
        if (getWidth() > getHeight()) {
            radius = getHeight() / 2;
        } else {
            radius = getWidth() / 2;
        }
        return radius;
    }

    private double getRadiusMissedRing() {
        double radiusMissedRing = (float) (getRadius() * 0.99);
        return radiusMissedRing;
    }

    private double getRadiusDoubleRing() {
        double radiusDoubleRing = (float) (getRadius() * 0.8);
        return radiusDoubleRing;
    }

    private double getRadiusUnderDoubleRing() {
        double radiusUnderDoubleRing = (float) (getRadius() * 0.8 * 0.95295);
        return radiusUnderDoubleRing;
    }

    private double getRadiusTrippleRing() {
        double radiusTrippleRing = (float) (getRadius() * 0.8 * 0.62941);
        return radiusTrippleRing;
    }

    private double getRadiusUnderTrippleRing() {
        double radiusUnderTrippleRing = (float) (getRadius() * 0.8 * 0.58235);
        return radiusUnderTrippleRing;
    }

    private double getRadiusSingleBull() {
        double radiusSingleBull = (float) (getRadius() * 0.8 * 0.09352);
        return radiusSingleBull;
    }

    private double getRadiusDoubleBull() {
        double radiusDoubleBull = (float) (getRadius() * 0.8 * 0.03735);
        return radiusDoubleBull;
    }


    ///***   methods to draw dartboard on canvas   ***///
    public void drawDartBoard(Canvas canvas) {
        Paint colorBlack = new Paint();
        colorBlack.setColor(Color.BLACK);
        Paint colorWhite = new Paint();
        colorWhite.setColor(Color.WHITE);
        Paint colorRed = new Paint();
        colorRed.setColor(Color.RED);
        Paint colorGreen = new Paint();
        colorGreen.setColor(Color.GREEN);
        Paint colorText = new Paint();
        colorText.setColor(Color.WHITE);
        colorText.setTextSize(40);

        drawDartBoardCircle(canvas, getRadiusMissedRing(), colorBlack);
        drawDartBoardRing(canvas, getRadiusDoubleRing(), colorRed, colorGreen);
        drawDartBoardRing(canvas, getRadiusUnderDoubleRing(), colorBlack, colorWhite);
        drawDartBoardRing(canvas, getRadiusTrippleRing(), colorRed, colorGreen);
        drawDartBoardRing(canvas, getRadiusUnderTrippleRing(), colorBlack, colorWhite);
        drawDartBoardCircle(canvas, getRadiusSingleBull(), colorGreen);
        drawDartBoardCircle(canvas, getRadiusDoubleBull(), colorRed);

        drawNumbers(dartPoints, canvas);
    }

    public void drawDartBoardCircle(Canvas canvas, double radius, Paint color) {
        float left = (float) (getCenterX() - radius);
        float top = (float) (getCenterY() - radius);
        float right = (float) (getCenterX() + radius);
        float bottom = (float) (getCenterY() + radius);

        RectF rectF = new RectF();
        rectF.set(left, top, right, bottom);
        canvas.drawArc(rectF, 0, 360, true, color);
    }

    private void drawDartBoardRing(Canvas canvas, double radius, Paint color1, Paint color2) {
        float left = (float) (getCenterX() - radius);
        float top = (float) (getCenterY() - radius);
        float right = (float) (getCenterX() + radius);
        float bottom = (float) (getCenterY() + radius);

        RectF rectF = new RectF();
        rectF.set(left, top, right, bottom);

        boolean booleanColor1 = true;
        int leftStart = -9;
        for (int i = 0; i < 20; i++) {
            leftStart += 18;
            if (booleanColor1 == true) {
                canvas.drawArc(rectF, leftStart, 18, true, color1);
                booleanColor1 = false;
            } else {
                canvas.drawArc(rectF, leftStart, 18, true, color2);
                booleanColor1 = true;
            }
        }
    }

    public void drawNumbers(ArrayList<Integer> dartPoints, Canvas canvas) {
        Paint colorText = new Paint();
        colorText.setColor(Color.WHITE);
        colorText.setTextSize(40);

        float xRightCalc = (float) (getCenterX() + getRadiusDoubleRing() + ((getRadiusMissedRing() - getRadiusDoubleRing()) / 2) * 0.8);
        float yRightCalc = (float) getCenterY();
        float rotatedXRight = xRightCalc;
        float rotatedYRight = yRightCalc;

        float xLeftCalc = (float) (getCenterX() - getRadiusDoubleRing() - ((getRadiusMissedRing() - getRadiusDoubleRing()) / 2) * 0.8);
        float yLeftCalc = (float) getCenterY();
        float rotatedXLeft = xLeftCalc;
        float rotatedYLeft = yLeftCalc;

        int angle = 18;
        for (int i = 0; i < 10; i++) {
            canvas.drawText(dartPoints.get(i).toString(), rotatedXRight, rotatedYRight, colorText);
            canvas.drawText(dartPoints.get(i + 10).toString(), rotatedXLeft, rotatedYLeft, colorText);

            float angleRadians = (float) (angle * (Math.PI / 180)); // Convert to radians

            rotatedXRight = (float) (Math.cos(angleRadians) * (xRightCalc - getCenterX()) - Math.sin(angleRadians) * (yRightCalc - getCenterY()) + getCenterX());
            rotatedYRight = (float) (Math.sin(angleRadians) * (xRightCalc - getCenterX()) + Math.cos(angleRadians) * (yRightCalc - getCenterY()) + getCenterY());

            rotatedXLeft = (float) (Math.cos(angleRadians) * (xLeftCalc - getCenterX()) - Math.sin(angleRadians) * (yLeftCalc - getCenterY()) + getCenterX());
            rotatedYLeft = (float) (Math.sin(angleRadians) * (xLeftCalc - getCenterX()) + Math.cos(angleRadians) * (yLeftCalc - getCenterY()) + getCenterY());

            angle += 18;
        }
    }

    ///***   methods to calculate clicked points   ***///
    private boolean checkClickedLeftUp(double clickedX, double clickedY) {
        boolean clickedLeftUp;
        if (clickedX <= getCenterX() && clickedY <= getCenterY()) {
            clickedLeftUp = true;
        } else {
            clickedLeftUp = false;
        }
        //System.out.println("checkClickedLeftUp: " + clickedLeftUp);
        return clickedLeftUp;
    }

    private boolean checkClickedLeftDown(double clickedX, double clickedY) {
        boolean clickedLeftDown;
        if (clickedX <= getCenterX() && clickedY >= getCenterY()) {
            clickedLeftDown = true;
        } else {
            clickedLeftDown = false;
        }
        //System.out.println("clickedLeftDown: " + clickedLeftDown);
        return clickedLeftDown;
    }

    private boolean checkClickedRightUp(double clickedX, double clickedY) {
        boolean clickedRightUp;
        if (clickedX >= getCenterX() && clickedY <= getCenterY()) {
            clickedRightUp = true;
        } else {
            clickedRightUp = false;
        }
        //System.out.println("clickedRightUp: " + clickedRightUp);
        return clickedRightUp;
    }

    private boolean checkCklickedRightDown(double clickedX, double clickedY) {
        boolean clickedRigthDown;
        if (clickedX >= getCenterX() && clickedY >= getCenterY()) {
            clickedRigthDown = true;
        } else {
            clickedRigthDown = false;
        }
        //System.out.println("clickedRigthDown: " + clickedRigthDown);
        return clickedRigthDown;
    }

    private double getA(double clickedX, double clickedY) {
        double a = 0;
        if (checkClickedLeftUp(clickedX, clickedY)) {
            a = getCenterX() - clickedX;
        } else if (checkClickedLeftDown(clickedX, clickedY)) {
            a = getCenterX() - clickedX;
        } else if (checkClickedRightUp(clickedX, clickedY)) {
            a = clickedX - getCenterX();
        } else if (checkCklickedRightDown(clickedX, clickedY)) {
            a = clickedX - getCenterX();
        }
        //System.out.println("a: " + a);
        return a;
    }

    private double getB(double clickedX, double clickedY) {
        double b = 0;
        if (checkClickedLeftUp(clickedX, clickedY)) {
            b = getCenterY() - clickedY;
        } else if (checkClickedLeftDown(clickedX, clickedY)) {
            b = clickedY - getCenterY();
        } else if (checkClickedRightUp(clickedX, clickedY)) {
            b = getCenterY() - clickedY;
        } else if (checkCklickedRightDown(clickedX, clickedY)) {
            b = clickedY - getCenterY();
        }
        //System.out.println("b: " + b);
        return b;
    }

    private double getC(double clickedX, double clickedY) {
        double c = Math.sqrt((Math.pow(getA(clickedX, clickedY), 2) + Math.pow(getB(clickedX, clickedY), 2)));
        //System.out.println("c: " + c);
        return c;
    }

    private double getAlpha(double clickedX, double clickedY) {
        double alpha = 0;
        if (checkClickedLeftUp(clickedX, clickedY)) {
            alpha = Math.toDegrees(Math.atan(getA(clickedX, clickedY) / getB(clickedX, clickedY)));
        } else if (checkClickedLeftDown(clickedX, clickedY)) {
            alpha = Math.toDegrees(Math.atan(getB(clickedX, clickedY) / getA(clickedX, clickedY)));
        } else if (checkClickedRightUp(clickedX, clickedY)) {
            alpha = Math.toDegrees(Math.atan(getB(clickedX, clickedY) / getA(clickedX, clickedY)));
        } else if (checkCklickedRightDown(clickedX, clickedY)) {
            alpha = Math.toDegrees(Math.atan(getA(clickedX, clickedY) / getB(clickedX, clickedY)));
        }
        //System.out.println("alpha: " + alpha);
        return alpha;
    }

    private double getBeta(double clickedX, double clickedY) {
        double beta = 180 - getAlpha(clickedX, clickedY) - getGamma();
        //System.out.println("beta: " + beta);
        return beta;
    }

    private double getGamma() {
        double gamma = 90;
        //System.out.println("gamma: " + gamma);
        return gamma;
    }

    private double getDegreeClickedPointToCenter(double clickedX, double clickedY) {
        double clickedDegree = 0;
        if (checkClickedLeftUp(clickedX, clickedY) == true) {
            clickedDegree = 180 + getBeta(clickedX, clickedY);
        } else if (checkClickedLeftDown(clickedX, clickedY) == true) {
            clickedDegree = 90 + getBeta(clickedX, clickedY);
        } else if (checkClickedRightUp(clickedX, clickedY) == true) {
            clickedDegree = 270 + getBeta(clickedX, clickedY);
        } else if (checkCklickedRightDown(clickedX, clickedY) == true) {
            clickedDegree = getBeta(clickedX, clickedY);
        }
        //System.out.println("clickedDegree: " + clickedDegree);
        return clickedDegree;
    }

    private boolean checkDoubleBullClicked(double clickedX, double clickedY) {
        boolean doubleBullClicked;
        if (getC(clickedX, clickedY) <= getRadiusDoubleBull()) {
            doubleBullClicked = true;
        } else {
            doubleBullClicked = false;
        }
        //System.out.println("doubleBullClicked: " + doubleBullClicked);
        return doubleBullClicked;
    }

    private boolean checkSingleBullClicked(double clickedX, double clickedY) {
        boolean singleBullClicked;
        if (getC(clickedX, clickedY) <= getRadiusSingleBull() && getC(clickedX, clickedY) > getRadiusDoubleBull()) {
            singleBullClicked = true;
        } else {
            singleBullClicked = false;
        }
        //System.out.println("singleBullClicked: " + singleBullClicked);
        return singleBullClicked;
    }

    private boolean checkUnderTrippleRingClicked(double clickedX, double clickedY) {
        boolean underTrippleRingClicked;
        if (getC(clickedX, clickedY) <= getRadiusUnderTrippleRing() && getC(clickedX, clickedY) > getRadiusSingleBull()) {
            underTrippleRingClicked = true;
        } else {
            underTrippleRingClicked = false;
        }
        //System.out.println("underTrippleRingClicked: " + underTrippleRingClicked);
        return underTrippleRingClicked;
    }

    private boolean checkTrippleRingClicked(double clickedX, double clickedY) {
        boolean trippleRingClicked;
        if (getC(clickedX, clickedY) <= getRadiusTrippleRing() && getC(clickedX, clickedY) > getRadiusUnderTrippleRing()) {
            trippleRingClicked = true;
        } else {
            trippleRingClicked = false;
        }
        //System.out.println("checkTrippleRingClicked: " + trippleRingClicked);
        return trippleRingClicked;
    }

    private boolean checkUnderDoubleRingClicked(double clickedX, double clickedY) {
        boolean underDoubleRingClicked;
        if (getC(clickedX, clickedY) <= getRadiusUnderDoubleRing() && getC(clickedX, clickedY) > getRadiusTrippleRing()) {
            underDoubleRingClicked = true;
        } else {
            underDoubleRingClicked = false;
        }
        //System.out.println("underDoubleRingClicked: " + underDoubleRingClicked);
        return underDoubleRingClicked;
    }

    private boolean checkDoubleRingClicked(double clickedX, double clickedY) {
        boolean doubleRingClicked;
        if (getC(clickedX, clickedY) <= getRadiusDoubleRing() && getC(clickedX, clickedY) > getRadiusUnderDoubleRing()) {
            doubleRingClicked = true;
        } else {
            doubleRingClicked = false;
        }
        //System.out.println("doubleRingClicked: " + doubleRingClicked);
        return doubleRingClicked;
    }

    private boolean checkMissedRingClicked(double clickedX, double clickedY) {
        boolean missedRingClicked;
        if (getC(clickedX, clickedY) > getRadiusDoubleRing()) {
            missedRingClicked = true;
        } else {
            missedRingClicked = false;
        }
        //System.out.println("missedRingClicked: " + missedRingClicked);
        return missedRingClicked;
    }

    public int getDartPointsHit(double clickedX, double clickedY) {
        int dartPointsHit = 0;

        double degreeClickedPointToCenter = getDegreeClickedPointToCenter(clickedX, clickedY);
        if (checkMissedRingClicked(clickedX, clickedY) == true) {
            dartPointsHit = 0;
        } else if (checkSingleBullClicked(clickedX, clickedY)) {
            dartPointsHit = 25;
        } else if (checkDoubleBullClicked(clickedX, clickedY)) {
            dartPointsHit = 50;
        } else if (degreeClickedPointToCenter >= 351 || degreeClickedPointToCenter <= 9) {
            dartPointsHit = dartPoints.get(0);
        } else if (degreeClickedPointToCenter > 9 && degreeClickedPointToCenter <= 27) {
            dartPointsHit = dartPoints.get(1);
        } else if (degreeClickedPointToCenter > 27 && degreeClickedPointToCenter <= 45) {
            dartPointsHit = dartPoints.get(2);
        } else if (degreeClickedPointToCenter > 45 && degreeClickedPointToCenter <= 63) {
            dartPointsHit = dartPoints.get(3);
        } else if (degreeClickedPointToCenter > 63 && degreeClickedPointToCenter <= 81) {
            dartPointsHit = dartPoints.get(4);
        } else if (degreeClickedPointToCenter > 81 && degreeClickedPointToCenter <= 99) {
            dartPointsHit = dartPoints.get(5);
        } else if (degreeClickedPointToCenter > 99 && degreeClickedPointToCenter <= 117) {
            dartPointsHit = dartPoints.get(6);
        } else if (degreeClickedPointToCenter > 117 && degreeClickedPointToCenter <= 135) {
            dartPointsHit = dartPoints.get(7);
        } else if (degreeClickedPointToCenter > 135 && degreeClickedPointToCenter <= 153) {
            dartPointsHit = dartPoints.get(8);
        } else if (degreeClickedPointToCenter > 153 && degreeClickedPointToCenter <= 171) {
            dartPointsHit = dartPoints.get(9);
        } else if (degreeClickedPointToCenter > 171 && degreeClickedPointToCenter <= 189) {
            dartPointsHit = dartPoints.get(10);
        } else if (degreeClickedPointToCenter > 189 && degreeClickedPointToCenter <= 207) {
            dartPointsHit = dartPoints.get(11);
        } else if (degreeClickedPointToCenter > 207 && degreeClickedPointToCenter <= 225) {
            dartPointsHit = dartPoints.get(12);
        } else if (degreeClickedPointToCenter > 225 && degreeClickedPointToCenter <= 243) {
            dartPointsHit = dartPoints.get(13);
        } else if (degreeClickedPointToCenter > 243 && degreeClickedPointToCenter <= 261) {
            dartPointsHit = dartPoints.get(14);
        } else if (degreeClickedPointToCenter > 261 && degreeClickedPointToCenter <= 279) {
            dartPointsHit = dartPoints.get(15);
        } else if (degreeClickedPointToCenter > 279 && degreeClickedPointToCenter <= 297) {
            dartPointsHit = dartPoints.get(16);
        } else if (degreeClickedPointToCenter > 297 && degreeClickedPointToCenter <= 315) {
            dartPointsHit = dartPoints.get(17);
        } else if (degreeClickedPointToCenter > 315 && degreeClickedPointToCenter <= 333) {
            dartPointsHit = dartPoints.get(18);
        } else if (degreeClickedPointToCenter > 333 && degreeClickedPointToCenter < 351) {
            dartPointsHit = dartPoints.get(19);
        }

        if (checkTrippleRingClicked(clickedX, clickedY)) {
            dartPointsHit *= 3;
        } else if (checkDoubleRingClicked(clickedX, clickedY)) {
            dartPointsHit *= 2;
        }

        //System.out.println("points hit: " + dartPointsHit);
        return dartPointsHit;
    }

}

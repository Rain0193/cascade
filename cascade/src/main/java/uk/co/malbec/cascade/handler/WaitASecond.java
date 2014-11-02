package uk.co.malbec.cascade.handler;


public class WaitASecond implements Handler {

    @Override
    public void handle(Object step) {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {}
    }
}
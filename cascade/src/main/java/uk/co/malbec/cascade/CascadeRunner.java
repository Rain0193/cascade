package uk.co.malbec.cascade;

import org.junit.runner.Description;
import org.junit.runner.Runner;
import org.junit.runner.notification.RunNotifier;

public class CascadeRunner extends Runner {

    private Cascade cascade;

    public CascadeRunner(Class<?> testClass) {
        cascade = new Cascade(new ReflectionsClasspathScanner());
        cascade.init(testClass);
    }

    @Override
    public Description getDescription() {
        return cascade.getDescription();
    }

    @Override
    public void run(RunNotifier notifier) {
        cascade.run(notifier);
    }

}
package xyz.azide.ui.font.api;

import xyz.azide.trait.Manager;
import xyz.azide.ui.font.FontRenderer;

import java.awt.*;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

public class FontManager implements Manager {
    private static final AtomicInteger completed = new AtomicInteger(0);
    private static final int THREADS = 1;
    private static CountDownLatch latch;
    public FontRenderer montserrat = new FontRenderer("Montserrat", 20, Font.PLAIN, true, true);
    public FontRenderer poppins = new FontRenderer("Poppins", 20, Font.PLAIN, true, true);

    private void loadFonts() {
        completed.incrementAndGet();
        latch.countDown();
    }

    @Override
    public void initialize() {
        final ExecutorService executor = Executors.newFixedThreadPool(THREADS);
        final CountDownLatch allThreadsDone = new CountDownLatch(THREADS);
        latch = new CountDownLatch(THREADS);

        for (int i = 0; i < THREADS; i++) {
            executor.submit(() -> {
                loadFonts();
                allThreadsDone.countDown();
            });
        }

        executor.shutdown();

        try {
            allThreadsDone.await();
            latch.await();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
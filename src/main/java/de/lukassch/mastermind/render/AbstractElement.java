package de.lukassch.mastermind.render;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;

public abstract class AbstractElement {

    private Runnable onChange;

    public final void onChange(Runnable onChange) {
        this.onChange = onChange;
    }

    /**
     * Called if the canvas was resized.
     *
     * @param canvas The canvas that was resized
     */
    public abstract void onResize(Canvas canvas);

    /**
     * Called if the canvas was clicked.
     *
     * @param event The event that was called
     */
    public abstract void onClick(MouseEvent event);

    public abstract void onKeyPress(KeyEvent event);

    public abstract void onDraw(Canvas canvas, GraphicsContext context);

    /**
     * Called if the user is scrolling with the mouse wheel.
     *
     * @param event The called scroll event
     */
    public void onScroll(ScrollEvent event) {
    }

    protected final void callChange() {
        if (this.onChange != null) {
            this.onChange.run();
        }
    }
}

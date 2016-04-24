package xeyes;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.geom.Ellipse2D;

import javax.swing.JComponent;

/** XEyes のひとつの目を描画するコンポーネントです。 */
public final class EyeComponent extends JComponent {
    /** このクラスのシリアルバージョン ID です。 */
    private static final long serialVersionUID = 1L;

    /** コンポーネントの動作を定義するモデルです。 */
    private final EyeComponentModel model = new EyeComponentModel();

    /** 新しい目のコンポーネントを作成します。 */
    public EyeComponent() {
        super();
        addComponentListener(sizeChangedListener);
    }

    /** コンポーネントのサイズが変わった時に呼び出されるリスナーです。 */
    private final ComponentListener sizeChangedListener =
            new ComponentAdapter() {
                @Override
                public void componentResized(ComponentEvent e) {
                    model.setEyeWidth(getWidth());
                    model.setEyeHeight(getHeight());
                }
            };

    /**
     * 目の描画を行います。
     * @param g 描画に使用するグラフィック
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = null;
        try {
            g2 = (Graphics2D) g.create();
            g2.setPaint(Color.WHITE);
            g2.fill(new Ellipse2D.Double(0.0, 0.0, model.getEyeWidth(), model
                    .getEyeHeight()));
            g2.setPaint(Color.BLACK);
            g2.fill(new Ellipse2D.Double(model.getIrisX(), model.getIrisY(),
                    model.getIrisWidth(), model.getIrisHeight()));
        } finally {
            if (g2 != null)
                g2.dispose();
        }
    }

    /**
     * コンポーネントのサイズに対する瞳のサイズの比率を取得します。
     * @return コンポーネントのサイズに対する瞳のサイズの比率
     */
    public double getIrisSizeRatio() {
        return model.getIrisSizeRatio();
    }

    /**
     * コンポーネントのサイズに対する瞳のサイズの比率を設定します。
     * @param irisSizeRatio コンポーネントのサイズに対する瞳のサイズの比率
     * @throws IllegalArgumentException 指定された値が 0 以下または 1 以上の場合
     */
    public void setIrisSizeRatio(float irisSizeRatio) {
        model.setIrisSizeRatio(irisSizeRatio);
        repaint();
    }

    /**
     * 瞳が指定された位置を向くようにします。
     * @param focus 視線の先を示す位置
     */
    public void lookAt(Point focus) {
        model.lookAt(focus.getX(), focus.getY());
        repaint();
    }
}

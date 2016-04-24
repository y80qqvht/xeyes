package xeyes.layout;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.LayoutManager2;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/** 大きさが不均一なセルをもつグリッドへコンポーネントを追加するレイアウトマネージャーです。 */
public class NonUniformGridLayout implements LayoutManager2 {
    /** ウェイトのデフォルトとして使用される配列 */
    private static final float[] DEFAULT_WEIGHTS = { 1.0f };

    /** 各行の割り当てスペースを決定するためのウェイト */
    private final float[] rowWeights;

    /** 各列の割り当てスペースを決定するためのウェイト */
    private final float[] columnWeights;

    /** 1 行 1 列にコンポーネントを配置するレイアウトマネージャーを作成します。 */
    public NonUniformGridLayout() {
        this(DEFAULT_WEIGHTS, DEFAULT_WEIGHTS);
    }

    /**
     * 指定されたウェイトをもつレイアウトマネージャーを作成します。
     * @param rowWeights 各行の割り当てスペースを決定するためのウェイト
     * @param columnWeights 各列の割り当てスペースを決定するためのウェイト
     * @throws NullPointerException 指定されたウェイトのいずれかが {@code null} の場合
     * @throws IllegalArgumentException 指定されたウェイトの行数、または列数が {@code 0}
     * の場合、または指定されたウェイトが {@code 0.0f} 以下の要素を含む場合
     */
    public NonUniformGridLayout(float[] rowWeights, float[] columnWeights) {
        this.rowWeights = Arrays.copyOf(rowWeights, rowWeights.length);
        this.columnWeights = Arrays.copyOf(columnWeights, columnWeights.length);

        if (this.rowWeights.length == 0)
            throw new IllegalArgumentException("rowWeights is empty.");
        else if (this.columnWeights.length == 0)
            throw new IllegalArgumentException("columnWeights is empty.");

        for (float weight : this.rowWeights) {
            if (weight <= 0.0f)
                throw new IllegalArgumentException("rowWeight: " + weight);
        }
        for (float weight : this.columnWeights) {
            if (weight <= 0.0f)
                throw new IllegalArgumentException("columnWeight: " + weight);
        }

        normalizeWeights(this.rowWeights);
        normalizeWeights(this.columnWeights);
    }

    /**
     * 指定されたウェイトの配列を、要素の合計が {@code 1.0f} となるように正規化します。
     * @param weights 正規化するウェイト
     */
    private static void normalizeWeights(float[] weights) {
        float sum = 0.0f;
        for (float weight : weights) {
            assert weight > 0.0f;
            sum += weight;
        }
        for (int i = 0, n = weights.length; i < n; ++i)
            weights[i] /= sum;
    }

    /**
     * グリッドの行数を取得します。
     * @return グリッドの行数
     */
    public int getRowCount() {
        return rowWeights.length;
    }

    /**
     * 指定された行番号のウェイトを取得します。
     * @param row 行番号
     * @return 指定された行番号のウェイト
     * @throws IndexOutOfBoundsException 指定された行番号が不正な場合
     */
    public float getRowWeight(int row) {
        if (row < 0 || row >= getRowCount())
            throw new IndexOutOfBoundsException("row: " + row);
        return rowWeights[row];
    }

    /**
     * グリッドの列数を取得します。
     * @return グリッドの列数
     */
    public int getColumnCount() {
        return columnWeights.length;
    }

    /**
     * 指定された列番号のウェイトを取得します。
     * @param column 列番号
     * @return 指定された列番号のウェイト
     * @throws IndexOutOfBoundsException 指定された列番号が不正な場合
     */
    public float getColumnWeight(int column) {
        if (column < 0 || column >= getColumnCount())
            throw new IndexOutOfBoundsException("column: " + column);
        return columnWeights[column];
    }

    /**
     * このレイアウトマネージャーの文字列表現を作成します。
     * @return このレイアウトマネージャーの文字列表現
     */
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder(64);
        builder.append(getClass().getSimpleName());
        builder.append(" {rowWeights: ");
        builder.append(Arrays.toString(rowWeights));
        builder.append(", columnWeights: ");
        builder.append(Arrays.toString(columnWeights));
        builder.append('}');
        return builder.toString();
    }

    /** レイアウトを行う子コンポーネントと配置先のセルを結びつけるマップ */
    private final Map<Component, GridCell> componentMap =
            new HashMap<Component, GridCell>();

    /**
     * このレイアウトマネージャーはコンポーネントごとの文字列を使用しないため、何もしません。
     * @param name コンポーネントに関連付けられた文字列
     * @param comp 追加されるコンポーネント
     */
    @Override
    public void addLayoutComponent(String name, Component comp) {
    }

    /**
     * 指定されたセルの位置へコンポーネントを追加します。指定されたセルが {@code null} の場合は何も行いません。
     * @param comp 追加されるコンポーネント
     * @param gridCell 配置先のセル
     * @throws NullPointerException コンポーネントが {@code null} の場合
     * @throws IllegalArgumentException 指定された制約が {@link GridCell}
     * でない場合、または指定されたセルがこのグリッドの外側に位置する場合
     */
    @Override
    public void addLayoutComponent(Component comp, Object gridCell) {
        if (gridCell != null) {
            if (comp == null)
                throw new NullPointerException("comp is null.");
            else if (!(gridCell instanceof GridCell))
                throw new IllegalArgumentException(
                        "constraints is not GridCell.");

            GridCell cell = (GridCell) gridCell;
            if (!contains(cell))
                throw new IllegalArgumentException("not contains " + cell);
            componentMap.put(comp, cell);
        }
    }

    /**
     * 指定されたコンポーネントを削除します。
     * @param comp 削除されるコンポーネント
     * @throws NullPointerException コンポーネントが {@code null} の場合
     */
    @Override
    public void removeLayoutComponent(Component comp) {
        if (comp == null)
            throw new NullPointerException("comp is null.");
        componentMap.remove(comp);
    }

    /*
     * (非 Javadoc)
     * @see java.awt.LayoutManager#preferredLayoutSize(java.awt.Container)
     */
    @Override
    public Dimension preferredLayoutSize(Container parent) {
        return getLayoutSize(parent, ComponentSizeType.PREFERRED_SIZE);
    }

    /*
     * (非 Javadoc)
     * @see java.awt.LayoutManager#minimumLayoutSize(java.awt.Container)
     */
    @Override
    public Dimension minimumLayoutSize(Container parent) {
        return getLayoutSize(parent, ComponentSizeType.MINIMUM_SIZE);
    }

    /**
     * 指定されたコンテナの最大サイズを取得します。この実装では常に幅と高さが {@link java.lang.Integer#MAX_VALUE} の
     * {@link java.awt.Dimension} を返します。
     * @param parent 最大サイズを取得するコンテナ
     * @return 指定されたコンテナの最大サイズ
     */
    @Override
    public Dimension maximumLayoutSize(Container parent) {
        return new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE);
    }

    /*
     * (非 Javadoc)
     * @see java.awt.LayoutManager#layoutContainer(java.awt.Container)
     */
    @Override
    public void layoutContainer(Container parent) {
        setComponentLayoutBounds(parent, ComponentSizeType.PREFERRED_SIZE);
    }

    /**
     * 指定されたコンテナのレイアウトを無効にします。
     * @param parent 配置されるコンテナ
     */
    @Override
    public void invalidateLayout(Container parent) {
    }

    /**
     * x 軸方向の配置を返します。 この実装では、常に {@link java.awt.Component#CENTER_ALIGNMENT}
     * を返します。
     * @param parent 配置されるコンテナ
     * @return 常に {@link java.awt.Component#CENTER_ALIGNMENT}
     */
    @Override
    public float getLayoutAlignmentX(Container parent) {
        return Component.CENTER_ALIGNMENT;
    }

    /**
     * y 軸方向の配置を返します。 この実装では、常に {@link java.awt.Component#CENTER_ALIGNMENT}
     * を返します。
     * @param parent 配置されるコンテナ
     * @return 常に {@link java.awt.Component#CENTER_ALIGNMENT}
     */
    @Override
    public float getLayoutAlignmentY(Container parent) {
        return Component.CENTER_ALIGNMENT;
    }

    /**
     * 指定された {@code int} 配列の指定された範囲内にある要素の合計値を計算します。
     * @param array 対象の {@code int} 配列
     * @param from 計算する最初のインデックス（範囲内）
     * @param to 計算する最後のインデックス（範囲外）
     * @return 計算結果の合計値
     * @throws IllegalArgumentException {@code from > to} の場合
     * @throws NullPointerException 指定された配列が {@code null} の場合
     * @throws ArrayIndexOutOfBoundsException {@code from < 0} または
     * {@code to > array.length} の場合
     */
    private static int sum(int[] array, int from, int to) {
        if (from > to)
            throw new IllegalArgumentException("from: " + from + ", to: " + to);
        int sum = 0;
        for (int i = from; i < to; ++i)
            sum += array[i];
        return sum;
    }

    /**
     * 指定された {@code float} 配列の指定された範囲内にある要素の合計値を計算します。
     * @param array 対象の {@code float} 配列
     * @param from 計算する最初のインデックス（範囲内）
     * @param to 計算する最後のインデックス（範囲外）
     * @return 計算結果の合計値
     * @throws IllegalArgumentException {@code from > to} の場合
     * @throws NullPointerException 指定された配列が {@code null} の場合
     * @throws ArrayIndexOutOfBoundsException {@code from < 0} または
     * {@code to > array.length} の場合
     */
    private static float sum(float[] array, int from, int to) {
        if (from > to)
            throw new IllegalArgumentException("from: " + from + ", to: " + to);
        float sum = 0.0f;
        for (int i = from; i < to; ++i)
            sum += array[i];
        return sum;
    }

    /**
     * 指定されたコンポーネントの配置先となるセルを取得します。
     * @param comp 配置先を探すコンポーネント
     * @return 配置先のセル
     */
    private GridCell getCell(Component comp) {
        GridCell cell = componentMap.get(comp);
        if (cell == null)
            cell = GridCell.DEFAULT;
        return cell;
    }

    /**
     * 指定されたセルがこのグリッドに含まれるかどうかを確認します。
     * @param cell 対象のセル
     * @return 指定されたセルがこのグリッドに含まれる場合は {@code true}、そうでない場合は {@code false}
     */
    private boolean contains(GridCell cell) {
        int row = cell.getRow();
        int rowSpan = cell.getRowSpan();
        int rowCount = getRowCount();
        int column = cell.getColumn();
        int columnSpan = cell.getColumnSpan();
        int columnCount = getColumnCount();
        return row < rowCount && row + rowSpan <= rowCount
                && column < columnCount && column + columnSpan <= columnCount;
    }

    /**
     * 指定されたコンテナの指定された種類のサイズを計算します。
     * @param parent サイズを計算するコンテナ
     * @param sizeType 計算に使用するサイズの種類
     * @return コンテナのサイズ
     */
    private Dimension getLayoutSize(Container parent, ComponentSizeType sizeType) {
        int width = 0;
        int height = 0;

        for (int i = 0, n = parent.getComponentCount(); i < n; ++i) {
            Component comp = parent.getComponent(i);
            Dimension size = sizeType.getSize(comp);
            GridCell cell = getCell(comp);
            assert contains(cell);

            int columnStart = cell.getColumn();
            int columnEnd = columnStart + cell.getColumnSpan();
            float columnWeight = sum(columnWeights, columnStart, columnEnd);
            width = Math.max(width, Math.round(size.width / columnWeight));

            int rowStart = cell.getRow();
            int rowEnd = rowStart + cell.getRowSpan();
            float rowWeight = sum(rowWeights, rowStart, rowEnd);
            height = Math.max(height, Math.round(size.height / rowWeight));
        }

        Insets insets = parent.getInsets();
        width += insets.left + insets.right;
        height += insets.top + insets.bottom;
        return new Dimension(width, height);
    }

    /**
     * 指定されたコンテナ内にあるコンポーネントの位置とサイズを計算して設定します。
     * @param parent 対象のコンテナ
     * @param sizeType 計算に使用するするサイズの種類
     */
    private void setComponentLayoutBounds(Container parent,
            ComponentSizeType sizeType) {
        Insets insets = parent.getInsets();
        int width = parent.getWidth() - insets.left - insets.right;
        int height = parent.getHeight() - insets.top - insets.bottom;

        int columnCount = getColumnCount();
        int[] columnWidth = new int[columnCount];
        for (int i = 0; i < columnCount; ++i)
            columnWidth[i] = Math.round(width * getColumnWeight(i));

        int rowCount = getRowCount();
        int[] rowHeight = new int[rowCount];
        for (int i = 0; i < rowCount; ++i)
            rowHeight[i] = Math.round(height * getRowWeight(i));

        for (int i = 0, n = parent.getComponentCount(); i < n; ++i) {
            Component comp = parent.getComponent(i);
            GridCell cell = getCell(comp);
            assert contains(cell);
            int columnStart = cell.getColumn();
            int columnEnd = columnStart + cell.getColumnSpan();
            int x = sum(columnWidth, 0, columnStart);
            int w = sum(columnWidth, columnStart, columnEnd);
            Dimension size = null;
            if (!cell.isHorizontallyFilled()) {
                if (size == null)
                    size = sizeType.getSize(comp);
                int prefWidth = size.width;
                if (prefWidth < w) {
                    float alignment = comp.getAlignmentX();
                    x += Math.round((w - prefWidth) * alignment);
                    w = prefWidth;
                }
            }
            int rowStart = cell.getRow();
            int rowEnd = rowStart + cell.getRowSpan();
            int y = sum(rowHeight, 0, rowStart);
            int h = sum(rowHeight, rowStart, rowEnd);
            if (!cell.isVerticallyFilled()) {
                if (size == null)
                    size = sizeType.getSize(comp);
                int prefHeight = size.height;
                if (prefHeight < h) {
                    float alignment = comp.getAlignmentY();
                    y += Math.round((h - prefHeight) * alignment);
                    h = prefHeight;
                }
            }
            x += insets.left;
            y += insets.top;
            comp.setBounds(x, y, w, h);
        }
    }

    /** コンポーネントのサイズの種類です。 */
    private static enum ComponentSizeType {
        /** コンポーネントの現在のサイズ */
        SIZE {
            @Override
            public Dimension getSize(Component comp) {
                return comp.getSize();
            }
        },

        /** コンポーネントの推奨サイズ */
        PREFERRED_SIZE {
            @Override
            public Dimension getSize(Component comp) {
                return comp.getPreferredSize();
            }
        },

        /** コンポーネントの最小サイズ */
        MINIMUM_SIZE {
            @Override
            public Dimension getSize(Component comp) {
                return comp.getMinimumSize();
            }
        },

        /** コンポーネントの最大サイズ */
        MAXIMUM_SIZE {
            @Override
            public Dimension getSize(Component comp) {
                return comp.getMaximumSize();
            }
        };

        /**
         * 指定されたコンポーネントのサイズを取得します。
         * @param comp サイズを取得するコンポーネント
         * @return 取得したサイズ
         * @throws NullPointerException 指定されたコンポーネントが {@code null} の場合
         */
        public abstract Dimension getSize(Component comp);
    }
}

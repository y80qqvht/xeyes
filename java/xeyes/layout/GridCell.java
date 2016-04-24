package xeyes.layout;

/** {@link NonUniformGridLayout} 内のセルです。 */
public final class GridCell {
    /**
     * 行番号 0、列番号 0、行数 1、列数 1 を表すセルです。これは引数なしのコンストラクタで作成したセルと同一のものです。
     * @see #GridCell()
     */
    public static final GridCell DEFAULT = new GridCell();

    /** このセルの行番号 */
    private final int row;

    /** このセルの列番号 */
    private final int column;

    /** このセルが占める行数 */
    private final int rowSpan;

    /** このセルが占める列数 */
    private final int columnSpan;

    /** セル内のコンポーネントが利用可能な水平方向領域を占有するかどうか */
    private final boolean horizontallyFilled;

    /** セル内のコンポーネントが利用可能な垂直方向領域を占有するかどうか */
    private final boolean verticallyFilled;

    /**
     * 行番号 0、列番号 0、行数 1、列数 1 のセルを構築します。
     * @see #DEFAULT
     */
    public GridCell() {
        this(0, 0);
    }

    /**
     * 指定された位置にある、行数 1、列数 1 のセルを構築します。
     * @param row セルの行番号
     * @param column セルの列番号
     * @throws IndexOutOfBoundsException 指定された情報が不正な場合
     */
    public GridCell(int row, int column) {
        this(row, column, false, false);
    }

    /**
     * 指定された位置にあり、指定された行数、列数を占めるセルを構築します。
     * @param row セルの行番号
     * @param column セルの列番号
     * @param rowSpan セルが占める行数
     * @param columnSpan セルが占める列数
     * @throws IndexOutOfBoundsException 指定された情報が不正な場合
     */
    public GridCell(int row, int column, int rowSpan, int columnSpan) {
        this(row, column, rowSpan, columnSpan, false, false);
    }

    /**
     * 指定された位置にあり、指定されたようにセルを占有する、行数 1、列数 1 のセルを構築します。
     * @param row セルの行番号
     * @param column セルの列番号
     * @param horizontallyFilled セル内のコンポーネントが利用可能な水平方向領域を占有する場合は {@code true}
     * @param verticallyFilled セル内のコンポーネントが利用可能な垂直方向領域を占有する場合は {@code true}
     * @throws IndexOutOfBoundsException 指定された情報が不正な場合
     */
    public GridCell(int row, int column, boolean horizontallyFilled,
            boolean verticallyFilled) {
        this(row, column, 1, 1, horizontallyFilled, verticallyFilled);
    }

    /**
     * 指定された情報から {@link NonUniformGridLayout} のセルを構築します。
     * @param row セルの行番号
     * @param column セルの列番号
     * @param rowSpan セルが占める行数
     * @param columnSpan セルが占める列数
     * @param horizontallyFilled セル内のコンポーネントが利用可能な水平方向領域を占有する場合は {@code true}
     * @param verticallyFilled セル内のコンポーネントが利用可能な垂直方向領域を占有する場合は {@code true}
     * @throws IndexOutOfBoundsException 指定された情報が不正な場合
     */
    public GridCell(int row, int column, int rowSpan, int columnSpan,
            boolean horizontallyFilled, boolean verticallyFilled) {
        if (row < 0)
            throw new IndexOutOfBoundsException("row: " + row);
        if (column < 0)
            throw new IndexOutOfBoundsException("column: " + column);
        if (rowSpan < 1)
            throw new IndexOutOfBoundsException("rowSpan: " + rowSpan);
        if (columnSpan < 1)
            throw new IndexOutOfBoundsException("columnSpan: " + columnSpan);
        this.row = row;
        this.column = column;
        this.rowSpan = rowSpan;
        this.columnSpan = columnSpan;
        this.horizontallyFilled = horizontallyFilled;
        this.verticallyFilled = verticallyFilled;
    }

    /**
     * このセルの行番号を取得します。
     * @return このセルの行番号
     */
    public int getRow() {
        return row;
    }

    /**
     * このセルの列番号を取得します。
     * @return このセルの列番号
     */
    public int getColumn() {
        return column;
    }

    /**
     * このセルが占める行数を取得します。
     * @return このセルが占める行数
     */
    public int getRowSpan() {
        return rowSpan;
    }

    /**
     * このセルが占める列数を取得します。
     * @return このセルが占める列数
     */
    public int getColumnSpan() {
        return columnSpan;
    }

    /**
     * セル内のコンポーネントが利用可能な水平方向領域を占有するかどうかを取得します。
     * @return セル内のコンポーネントが利用可能な水平方向領域を占有する場合は {@code true}、そうでない場合は
     * {@code false}
     */
    public boolean isHorizontallyFilled() {
        return horizontallyFilled;
    }

    /**
     * セル内のコンポーネントが利用可能な垂直方向領域を占有するかどうかを取得します。
     * @return セル内のコンポーネントが利用可能な垂直方向領域を占有する場合は {@code true}、そうでない場合は
     * {@code false}
     */
    public boolean isVerticallyFilled() {
        return verticallyFilled;
    }

    /**
     * 指定されたオブジェクトがこのセルと同一の情報を持つかどうかを調べます。
     * @param obj 比較対象のオブジェクト
     * @return 指定されたオブジェクトがこのセルと同一の情報を持つ場合は {@code true}、そうでない場合は {@code false}
     * @see #hashCode()
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == this)
            return true;
        if (!(obj instanceof GridCell))
            return false;
        GridCell cell = (GridCell) obj;
        return cell.row == this.row && cell.column == this.column
                && cell.rowSpan == this.rowSpan
                && cell.columnSpan == this.columnSpan
                && cell.horizontallyFilled == this.horizontallyFilled
                && cell.verticallyFilled == this.verticallyFilled;
    }

    /**
     * このセルのハッシュコード値を計算します。
     * @return このセルのハッシュコード値
     * @see #equals(Object)
     */
    @Override
    public int hashCode() {
        int result = 17;
        result = 31 * result + row;
        result = 31 * result + column;
        result = 31 * result + rowSpan;
        result = 31 * result + columnSpan;
        result = 31 * result + (horizontallyFilled ? 1 : 0);
        result = 31 * result + (verticallyFilled ? 1 : 0);
        return result;
    }

    /**
     * このセルの文字列表現を作成します。
     * @return このセルの文字列表現
     */
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder(32);
        builder.append(getClass().getSimpleName());
        builder.append(" (").append(row);
        if (rowSpan > 1)
            builder.append(':').append(rowSpan);
        builder.append(", ").append(column);
        if (columnSpan > 1)
            builder.append(':').append(columnSpan);
        if (horizontallyFilled)
            builder.append(", horizontallyFilled");
        if (verticallyFilled)
            builder.append(", verticallyFilled");
        builder.append(')');
        return builder.toString();
    }
}

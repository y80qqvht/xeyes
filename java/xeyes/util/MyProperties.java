package xeyes.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.InvalidPropertiesFormatException;
import java.util.Properties;

/** {@link java.util.Properties} を使いやすくしたラッパークラスです。 */
public final class MyProperties {
    /** このプロパティの本体です。 */
    private final Properties prop;

    /** デフォルト値を持たない空のプロパティーリストを作成します。 */
    public MyProperties() {
        prop = new Properties();
    }

    /**
     * 指定されたファイルの XML ドキュメントにより表されるすべてのプロパティーを、このプロパティーテーブルにロードします。
     * 指定されたファイルが存在しない場合には何も行いません。
     * @param src XML ドキュメントの読み込み元のファイル
     * @throws IOException 入出力例外が発生した場合
     * @throws InvalidPropertiesFormatException 指定されたファイルが有効な XML ドキュメントではない場合
     * @throws NullPointerException {@code src} が {@code null} の場合
     */
    public void loadFromXMLFile(File src) throws IOException,
            InvalidPropertiesFormatException {
        if (src.exists()) {
            BufferedInputStream bis = null;
            FileInputStream fis = null;
            try {
                fis = new FileInputStream(src);
                bis = new BufferedInputStream(fis);
                prop.loadFromXML(bis);
            } finally {
                close(bis, fis);
            }
        }
    }

    /**
     * このテーブルに含まれるすべてのプロパティーを表す XML ドキュメントを指定されたファイルに発行します。
     * @param dest XML ドキュメントの発行先のファイル
     * @param comment プロパティーリストの説明。コメントが必要ない場合は {@code null}
     * @throws IOException 入出力例外が発生した場合
     * @throws NullPointerException {@code dest} が {@code null} の場合
     */
    public void storeToXMLFile(File dest, String comment) throws IOException {
        BufferedOutputStream bos = null;
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(dest);
            bos = new BufferedOutputStream(fos);
            prop.storeToXML(bos, comment);
        } finally {
            close(bos, fos);
        }
    }

    /**
     * 発生する {@link java.io.IOException} を無視して、指定されたオブジェクトをクローズします。
     * @param c1 クローズを行う 1 番目のオブジェクト
     * @param c2 クローズを行う 2 番目のオブジェクト
     */
    private static void close(Closeable c1, Closeable c2) {
        try {
            if (c1 != null)
                c1.close();
            if (c2 != null)
                c2.close();
        } catch (IOException e) {
        }
    }

    /**
     * 指定されたキーを持つ {@code boolean} 型のプロパティーを、プロパティーリストから探します。
     * そのプロパティーが見つからない場合、またはプロパティの値が {@code boolean} 型でない場合は、指定されたデフォルト値が返されます。
     * @param key プロパティーキー
     * @param defaultValue デフォルト値
     * @return 指定されたキー値を持つこのプロパティーリストの値
     */
    public boolean getBooleanProperty(String key, boolean defaultValue) {
        String stringValue = prop.getProperty(key);
        boolean value = defaultValue;
        if (stringValue != null) {
            if (stringValue.equals("true"))
                value = true;
            else if (stringValue.equals("false"))
                value = false;
        }
        return value;
    }

    /**
     * プロパティーリストの指定されたキーに指定された {@code boolean} 型の値を設定します。
     * @param key プロパティーキー
     * @param value キーに設定する値
     */
    public void setBooleanProperty(String key, boolean value) {
        prop.setProperty(key, String.valueOf(value));
    }

    /**
     * 指定されたキーを持つ {@code int} 型のプロパティーを、プロパティーリストから探します。
     * そのプロパティーが見つからない場合、またはプロパティの値が {@code int} 型でない場合は、指定されたデフォルト値が返されます。
     * @param key プロパティーキー
     * @param defaultValue デフォルト値
     * @return 指定されたキー値を持つこのプロパティーリストの値
     */
    public int getIntegerProperty(String key, int defaultValue) {
        String stringValue = prop.getProperty(key);
        int value = defaultValue;
        if (stringValue != null) {
            try {
                value = Integer.parseInt(stringValue);
            } catch (NumberFormatException e) {
                // デフォルト値のまま
            }
        }
        return value;
    }

    /**
     * プロパティーリストの指定されたキーに指定された {@code int} 型の値を設定します。
     * @param key プロパティーキー
     * @param value キーに設定する値
     */
    public void setIntegerProperty(String key, int value) {
        prop.setProperty(key, String.valueOf(value));
    }

    /**
     * 指定されたキーを持つ {@code long} 型のプロパティーを、プロパティーリストから探します。
     * そのプロパティーが見つからない場合、またはプロパティの値が {@code long} 型でない場合は、指定されたデフォルト値が返されます。
     * @param key プロパティーキー
     * @param defaultValue デフォルト値
     * @return 指定されたキー値を持つこのプロパティーリストの値
     */
    public long getLongProperty(String key, long defaultValue) {
        String stringValue = prop.getProperty(key);
        long value = defaultValue;
        if (stringValue != null) {
            try {
                value = Long.parseLong(stringValue);
            } catch (NumberFormatException e) {
                // デフォルト値のまま
            }
        }
        return value;
    }

    /**
     * プロパティーリストの指定されたキーに指定された {@code long} 型の値を設定します。
     * @param key プロパティーキー
     * @param value キーに設定する値
     */
    public void setLongProperty(String key, long value) {
        prop.setProperty(key, String.valueOf(value));
    }

    /**
     * 指定されたキーを持つ {@code float} 型のプロパティーを、プロパティーリストから探します。
     * そのプロパティーが見つからない場合、またはプロパティの値が {@code float} 型でない場合は、指定されたデフォルト値が返されます。
     * @param key プロパティーキー
     * @param defaultValue デフォルト値
     * @return 指定されたキー値を持つこのプロパティーリストの値
     */
    public float getFloatProperty(String key, float defaultValue) {
        String stringValue = prop.getProperty(key);
        float value = defaultValue;
        if (stringValue != null) {
            try {
                value = Float.parseFloat(stringValue);
            } catch (NumberFormatException e) {
                // デフォルト値のまま
            }
        }
        return value;
    }

    /**
     * プロパティーリストの指定されたキーに指定された {@code float} 型の値を設定します。
     * @param key プロパティーキー
     * @param value キーに設定する値
     */
    public void setFloatProperty(String key, float value) {
        prop.setProperty(key, String.valueOf(value));
    }

    /**
     * 指定されたキーを持つ {@code double} 型のプロパティーを、プロパティーリストから探します。
     * そのプロパティーが見つからない場合、またはプロパティの値が {@code double} 型でない場合は、指定されたデフォルト値が返されます。
     * @param key プロパティーキー
     * @param defaultValue デフォルト値
     * @return 指定されたキー値を持つこのプロパティーリストの値
     */
    public double getDoubleProperty(String key, double defaultValue) {
        String stringValue = prop.getProperty(key);
        double value = defaultValue;
        if (stringValue != null) {
            try {
                value = Double.parseDouble(stringValue);
            } catch (NumberFormatException e) {
                // デフォルト値のまま
            }
        }
        return value;
    }

    /**
     * プロパティーリストの指定されたキーに指定された {@code double} 型の値を設定します。
     * @param key プロパティーキー
     * @param value キーに設定する値
     */
    public void setDoubleProperty(String key, double value) {
        prop.setProperty(key, String.valueOf(value));
    }

    /**
     * 指定されたキーを持つ {@code String} 型のプロパティーを、プロパティーリストから探します。
     * そのプロパティーが見つからない場合は、指定されたデフォルト値が返されます。
     * @param key プロパティーキー
     * @param defaultValue デフォルト値
     * @return 指定されたキー値を持つこのプロパティーリストの値
     */
    public String getStringProperty(String key, String defaultValue) {
        return prop.getProperty(key, defaultValue);
    }

    /**
     * プロパティーリストの指定されたキーに指定された {@code String} 型の値を設定します。
     * @param key プロパティーキー
     * @param value キーに設定する値
     */
    public void setStringProperty(String key, String value) {
        prop.setProperty(key, value);
    }

    /**
     * 指定されたキーを持つプロパティーを、プロパティーリストから削除します。指定されたプロパティーが存在しない場合は何も行いません。
     * @param key プロパティーキー
     */
    public void removeProperty(String key) {
        prop.remove(key);
    }

    /**
     * 指定されたキーを持つプロパティーが、プロパティーリストに存在するかどうかを確認します。
     * @param key プロパティーキー
     * @return 指定されたキーを持つプロパティーがプロパティーリストに存在する場合は {@code true}、そうでない場合は
     * {@code false}
     */
    public boolean containsKey(String key) {
        return prop.containsKey(key);
    }
}

using System.Drawing;
using System.IO;
using System.Windows.Forms;
using System.Xml.Serialization;

namespace XEyesWinForm
{
    /// <summary>
    /// xeyes の設定管理クラスです。
    /// </summary>
    public sealed class Settings
    {
        /// <summary>
        /// フォームの左上の座標を表す設定です。
        /// </summary>
        private Point formLocation;

        /// <summary>
        /// フォームの大きさを表す設定です。
        /// </summary>
        private Size formSize;

        /// <summary>
        /// フォームの最大化・最小化の状態を表す設定です。
        /// </summary>
        private FormWindowState formWindowState;

        /// <summary>
        /// フォームを常に前面に表示するかどうかの設定です。
        /// </summary>
        private bool topMost;

        /// <summary>
        /// 終了前にメッセージボックスで確認するかどうかの設定です。
        /// </summary>
        private bool confirmOnExit;

        /// <summary>
        /// デフォルトの設定でこの管理クラスのインスタンスを作成します。
        /// </summary>
        public Settings()
        {
            formLocation = Point.Empty;
            formSize = Size.Empty;
            formWindowState = FormWindowState.Normal;
            topMost = false;
            confirmOnExit = true;
        }

        /// <summary>
        /// このインスタンスの設定を指定された設定と同じものにします。
        /// </summary>
        /// <param name="src">コピー元の設定</param>
        private void CopyFrom(Settings src)
        {
            if (this == src)
                return;
            this.formLocation = src.formLocation;
            this.formSize = src.formSize;
            this.formWindowState = src.formWindowState;
            this.topMost = src.topMost;
            this.confirmOnExit = src.confirmOnExit;
        }

        /// <summary>
        /// フォームの左上の座標を取得または設定します。
        /// </summary>
        public Point FormLocation
        {
            get
            {
                return formLocation;
            }
            set
            {
                formLocation = value;
            }
        }

        /// <summary>
        /// フォームの大きさを取得または設定します。
        /// </summary>
        public Size FormSize
        {
            get
            {
                return formSize;
            }
            set
            {
                formSize = value;
            }
        }

        /// <summary>
        /// フォームの最大化・最小化の状態を取得または設定します。
        /// </summary>
        public FormWindowState FormWindowState
        {
            get
            {
                return formWindowState;
            }
            set
            {
                formWindowState = value;
            }
        }

        /// <summary>
        /// フォームを常に前面に表示するかどうかを取得または設定します。
        /// </summary>
        public bool TopMost
        {
            get
            {
                return topMost;
            }
            set
            {
                topMost = value;
            }
        }

        /// <summary>
        /// 終了前にメッセージボックスで確認するかどうかを取得または設定します。
        /// </summary>
        public bool ConfirmOnExit
        {
            get
            {
                return confirmOnExit;
            }
            set
            {
                confirmOnExit = value;
            }
        }

        /// <summary>
        /// 指定されたファイルから設定を読み込みます。
        /// </summary>
        /// <param name="fileName">設定を読み込むファイルの名前</param>
        /// <exception cref="IOException">設定ファイルが存在しない場合</exception>
        public void Load(string fileName)
        {
            XmlSerializer serializer = new XmlSerializer(this.GetType());
            using (Stream stream = new FileStream(fileName, FileMode.Open))
                CopyFrom((Settings)serializer.Deserialize(stream));
        }

        /// <summary>
        /// 指定されたファイルへ現在の設定を書き出します。
        /// </summary>
        /// <param name="fileName">設定を書き出すファイルの名前</param>
        /// <exception cref="IOException">設定の書き出しに失敗した場合</exception>
        public void Store(string fileName)
        {
            XmlSerializer serializer = new XmlSerializer(this.GetType());
            using (Stream stream = new FileStream(fileName, FileMode.Create))
                serializer.Serialize(stream, this);
        }
    }
}

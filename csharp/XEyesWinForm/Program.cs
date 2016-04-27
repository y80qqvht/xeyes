using System;
using System.Configuration;
using System.Diagnostics;
using System.IO;
using System.Reflection;
using System.Text;
using System.Threading;
using System.Windows.Forms;
using XEyesWinForm.Configuration;

namespace XEyesWinForm
{
    static class Program
    {
        private static string _title;

        private static XEyesWinFormConfiguration _config;

        /// <summary>
        /// アプリケーションのメイン エントリ ポイントです。
        /// </summary>
        [STAThread]
        static void Main()
        {
            var assemblyName = Assembly.GetExecutingAssembly().GetName();
            _title = assemblyName.Name + ' ' + assemblyName.Version.ToString(3);

            ErrorCode errorCode = LoadConfig();
            if (errorCode == ErrorCode.NoError)
            {
                Application.SetUnhandledExceptionMode(UnhandledExceptionMode.CatchException);
                Application.ThreadException += OnUIThreadException;
                Application.ApplicationExit += OnApplicationExit;

                Application.EnableVisualStyles();
                Application.SetCompatibleTextRenderingDefault(false);
                Application.Run(new MainForm(_config));
            }
        }

        private static void OnApplicationExit(object sender, EventArgs e)
        {
            Application.ApplicationExit -= OnApplicationExit;
            Application.ThreadException -= OnUIThreadException;
        }

        private static void OnUIThreadException(object sender, ThreadExceptionEventArgs e)
        {
            var builder = new StringBuilder(256);
            builder.AppendLine("エラーが発生しました。");
            builder.AppendLine("アプリケーションは強制終了されます。");
            builder.AppendLine();
            builder.AppendLine("エラーの詳細：");
            builder.AppendLine(e.Exception.Message);
            builder.Append(e.Exception.StackTrace);
            string text = builder.ToString();

            builder.Length = 0;
            builder.Append("エラー - ");
            builder.Append(_title);
            string caption = builder.ToString();

            MessageBox.Show(text, caption, MessageBoxButtons.OK, MessageBoxIcon.Error);

            Application.Exit();
        }

        internal static string Title
        {
            get { return _title; }
        }

        internal enum ErrorCode
        {
            NoError,
            UnknownError,
            ConfigOpenError,
            ConfigSaveError
        }

        /// <summary>
        /// アプリケーションの構成ファイルを読み込みます。
        /// </summary>
        /// <returns>構成ファイルの読み込み結果を表すエラーコード</returns>
        private static ErrorCode LoadConfig()
        {
            try
            {
                string appPath = Assembly.GetExecutingAssembly().Location;
                string configFilePath = Path.ChangeExtension(appPath, ".xml");
                _config = XEyesWinFormConfiguration.Open(configFilePath);
                return ErrorCode.NoError;
            }
            catch (ConfigurationErrorsException e)
            {
                ErrorCode errorCode = ErrorCode.ConfigOpenError;
                ShowConfigurationErrorMessage(e, errorCode);
                return errorCode;
            }
        }

        /// <summary>
        /// 現在のアプリケーション設定を構成ファイルに書き込みます。
        /// </summary>
        /// <returns>構成ファイルの書き込み結果を表すエラーコード</returns>
        internal static ErrorCode SaveConfig()
        {
            try
            {
                Debug.Assert(_config != null, "_config is null.");
                _config.Save();
                return ErrorCode.NoError;
            }
            catch (ConfigurationErrorsException e)
            {
                ErrorCode errorCode = ErrorCode.ConfigSaveError;
                ShowConfigurationErrorMessage(e, errorCode);
                return errorCode;
            }
        }

        private static void ShowConfigurationErrorMessage(
            ConfigurationErrorsException e, ErrorCode errorCode)
        {
            var builder = new StringBuilder(256);
            switch (errorCode)
            {
                case ErrorCode.ConfigOpenError:
                    builder.AppendLine("設定ファイルの読み込みに失敗しました。");
                    builder.AppendLine("現在の設定ファイルを削除するかリネームしてください。");
                    break;
                case ErrorCode.ConfigSaveError:
                    builder.AppendLine("設定ファイルの書き込みに失敗しました。");
                    break;
                default:
                    builder.AppendLine("設定ファイルの取り扱いに失敗しました。");
                    break;
            }
            builder.AppendLine();
            builder.AppendLine("エラーの詳細：");
            builder.Append(e.BareMessage);
            string text = builder.ToString();

            builder.Length = 0;
            builder.Append("エラー - ");
            builder.Append(_title);
            string caption = builder.ToString();

            MessageBox.Show(text, caption, MessageBoxButtons.OK, MessageBoxIcon.Error);
        }
    }
}
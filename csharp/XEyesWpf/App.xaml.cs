using System.Configuration;
using System.Diagnostics;
using System.IO;
using System.Text;
using System.Windows;
using XEyesWpf.Configuration;

namespace XEyesWpf
{
    /// <summary>
    /// App.xaml の相互作用ロジック
    /// </summary>
    public partial class App : Application
    {
        private string _title;

        private XEyesWpfConfiguration _config;

        protected override void OnStartup(StartupEventArgs e)
        {
            base.OnStartup(e);

            var assemblyName = ResourceAssembly.GetName();
            _title = assemblyName.Name + ' ' + assemblyName.Version.ToString(3);

            ErrorCode errorCode = LoadConfig();
            if (errorCode == ErrorCode.NoError)
                new MainWindow(_config).Show();
            else
                Shutdown((int)errorCode);
        }

        protected override void OnExit(ExitEventArgs e)
        {
            if (e.ApplicationExitCode == 0)
            {
                ErrorCode errorCode;

                if (_config == null)
                    errorCode = ErrorCode.ConfigOpenError;
                else if (_config.XEyesSettings.SaveOnExit)
                    errorCode = SaveConfig();
                else
                    errorCode = ErrorCode.NoError;

                e.ApplicationExitCode = (int)errorCode;
            }

            base.OnExit(e);
        }

        internal string Title
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

        private ErrorCode LoadConfig()
        {
            try
            {
                string appPath = ResourceAssembly.Location;
                string configFilePath = Path.ChangeExtension(appPath, ".xml");
                _config = XEyesWpfConfiguration.Open(configFilePath);
                return ErrorCode.NoError;
            }
            catch (ConfigurationErrorsException e)
            {
                ErrorCode errorCode = ErrorCode.ConfigOpenError;
                ShowConfigurationErrorMessage(e, errorCode);
                return errorCode;
            }
        }

        internal ErrorCode SaveConfig()
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

        private void ShowConfigurationErrorMessage(
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
            string message = builder.ToString();

            builder.Length = 0;
            builder.Append("エラー - ");
            builder.Append(_title);
            string caption = builder.ToString();

            MessageBox.Show(message, caption, MessageBoxButton.OK, MessageBoxImage.Error);
        }

        private void App_DispatcherUnhandledException(
            object sender, System.Windows.Threading.DispatcherUnhandledExceptionEventArgs e)
        {
            var builder = new StringBuilder(256);
            builder.AppendLine("エラーが発生しました。");
            builder.AppendLine("アプリケーションは強制終了されます。");
            builder.AppendLine();
            builder.AppendLine("エラーの詳細：");
            builder.AppendLine(e.Exception.Message);
            builder.Append(e.Exception.StackTrace);
            string message = builder.ToString();

            builder.Length = 0;
            builder.Append("エラー - ");
            builder.Append(_title);
            string caption = builder.ToString();

            MessageBox.Show(message, caption, MessageBoxButton.OK, MessageBoxImage.Error);

            e.Handled = true;
            Shutdown((int)ErrorCode.UnknownError);
        }
    }
}

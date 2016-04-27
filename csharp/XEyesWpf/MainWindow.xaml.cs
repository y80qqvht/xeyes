using System;
using System.ComponentModel;
using System.Diagnostics;
using System.Windows;
using System.Windows.Threading;
using XEyesWpf.Configuration;
using XEyesWpf.WpfCommon;

namespace XEyesWpf
{
    /// <summary>
    /// MainWindow.xaml の相互作用ロジック
    /// </summary>
    public partial class MainWindow : Window
    {
        private readonly DispatcherTimer _timer = new DispatcherTimer();

        public MainWindow(XEyesWpfConfiguration config)
        {
            InitializeComponent();

            Debug.Assert(config != null, "config is null.");
            DataContext = new MainWindowModel(config);
            Title = GetApplication().Title;

            _timer.Interval = TimeSpan.FromMilliseconds(50.0);
            _timer.Tick += Timer_Tick;
            _timer.Start();
        }

        protected override void OnClosing(CancelEventArgs e)
        {
            base.OnClosing(e);

            if (!e.Cancel)
                _timer.Stop();
        }

        private static App GetApplication()
        {
            var app = Application.Current as App;
            Debug.Assert(app != null,
                "Current application is not App.");
            return app;
        }

        private MainWindowModel GetModel()
        {
            var model = DataContext as MainWindowModel;
            Debug.Assert(model != null,
                "Current DataContext is not MainWindowModel.");
            return model;
        }

        private void Timer_Tick(object sender, EventArgs e)
        {
            var focus = WpfUtilities.GetCursorPosition();
            leftEye.LookAt(leftEye.PointFromScreen(focus));
            rightEye.LookAt(rightEye.PointFromScreen(focus));
        }

        private void MenuOptions_Click(object sender, RoutedEventArgs e)
        {
            var optionsDialog = new OptionsDialog();
            optionsDialog.DataContext = this.DataContext;
            optionsDialog.Title = "設定 - " + this.Title;
            optionsDialog.Owner = this;
            optionsDialog.ShowDialog();
            e.Handled = true;
        }

        private void MenuSave_Click(object sender, RoutedEventArgs e)
        {
            GetApplication().SaveConfig();
            e.Handled = true;
        }

        private void MenuExit_Click(object sender, RoutedEventArgs e)
        {
            Close();
            e.Handled = true;
        }
    }
}

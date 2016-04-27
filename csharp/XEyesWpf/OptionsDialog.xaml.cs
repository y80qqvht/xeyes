using System.Windows;

namespace XEyesWpf
{
    /// <summary>
    /// SettingsDialog.xaml の相互作用ロジック
    /// </summary>
    public partial class OptionsDialog : Window
    {
        public OptionsDialog()
        {
            InitializeComponent();
        }

        private void OkButton_Click(object sender, RoutedEventArgs e)
        {
            DialogResult = true;
            e.Handled = true;
        }
    }
}

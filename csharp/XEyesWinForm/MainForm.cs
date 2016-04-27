using System;
using System.Diagnostics;
using System.Drawing;
using System.Windows.Forms;
using XEyesWinForm.Configuration;

namespace XEyesWinForm
{
    /// <summary>
    /// XEyes のメインフォームです。
    /// </summary>
    public partial class MainForm : Form
    {
        /// <summary>
        /// XEyes のメインフォームを作成します。
        /// </summary>
        public MainForm(XEyesWinFormConfiguration config)
        {
            InitializeComponent();

            Debug.Assert(config != null, "config is null.");
            DataSource = new MainFormModel(config);
            Text = Program.Title;
        }

        /// <summary>
        /// このフォームが初めて表示される直前に呼び出されます。
        /// </summary>
        /// <param name="e">
        /// イベント データを格納している
        /// <see cref="System.EventArgs"/>。
        /// </param>
        protected override void OnLoad(EventArgs e)
        {
            LoadSettings();

            base.OnLoad(e);
        }

        /// <summary>
        /// このフォームが閉じる前に呼び出されます。
        /// </summary>
        /// <param name="e">
        /// イベント データを格納している
        /// <see cref="System.Windows.Forms.FormClosingEventArgs"/>。
        /// </param>
        protected override void OnFormClosing(FormClosingEventArgs e)
        {
            base.OnFormClosing(e);

            if (!e.Cancel)
            {
                if (GetModel().SaveOnExit)
                    SaveSettings();
            }
        }

        private object DataSource
        {
            get { return modelBindingSource.DataSource; }
            set { modelBindingSource.DataSource = value; }
        }

        private MainFormModel GetModel()
        {
            var model = DataSource as MainFormModel;
            Debug.Assert(model != null);
            return model;
        }

        private void LoadSettings()
        {
            var model = GetModel();
            this.Location = new Point(model.FormLeft, model.FormTop);
            this.Size = new Size(model.FormWidth, model.FormHeight);
            this.WindowState = model.WindowState;
        }

        private void SaveSettings()
        {
            var state = this.WindowState;
            var bounds = (state == FormWindowState.Normal) ?
                new Rectangle(this.Location, this.Size) : this.RestoreBounds;
            var model = GetModel();
            model.FormLeft = bounds.Left;
            model.FormTop = bounds.Top;
            model.FormWidth = bounds.Width;
            model.FormHeight = bounds.Height;
            model.WindowState = state;
            Program.SaveConfig();
        }

        private void mainTimer_Tick(object sender, EventArgs e)
        {
            var focus = Cursor.Position;
            leftEye.LookAt(leftEye.PointToClient(focus));
            rightEye.LookAt(rightEye.PointToClient(focus));
        }

        private void optionsMenuItem_Click(object sender, EventArgs e)
        {
            using (var optionsDialog = new OptionsDialog())
            {
                optionsDialog.DataSource = this.DataSource;
                optionsDialog.Text = "設定 - " + this.Text;
                optionsDialog.ShowDialog(this);
            }
        }

        private void saveMenuItem_Click(object sender, EventArgs e)
        {
            SaveSettings();
        }

        private void exitMenuItem_Click(object sender, EventArgs e)
        {
            Close();
        }
    }
}

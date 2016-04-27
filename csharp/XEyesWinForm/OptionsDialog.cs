using System;
using System.Diagnostics;
using System.Windows.Forms;

namespace XEyesWinForm
{
    public partial class OptionsDialog : Form
    {
        public OptionsDialog()
        {
            InitializeComponent();

            var binding = irisSizeRatioTrackBar.DataBindings["Value"];
            Debug.Assert(binding != null, "Binding of Value is null.");
            binding.Format += irisSizeRatioTrackBarValueBinding_Format;
            binding.Parse += irisSizeRatioTrackBarValueBinding_Parse;
        }

        //protected override void OnLoad(EventArgs e)
        //{
        //    this.ActiveControl = irisSizeRatioTrackBar;
        //    base.OnLoad(e);
        //}

        //protected override void OnFormClosing(FormClosingEventArgs e)
        //{
        //    base.OnFormClosing(e);

        //    e.Cancel = true;
        //    Hide();
        //}

        internal object DataSource
        {
            get { return modelBindingSource.DataSource; }
            set { modelBindingSource.DataSource = value; }
        }

        private void irisSizeRatioTrackBarValueBinding_Format(object sender, ConvertEventArgs e)
        {
            object sourceValue = e.Value;
            Type targetType = e.DesiredType;
            Debug.Assert(sourceValue is float && targetType == typeof(int),
                string.Format("SourceType = {0}, TargetType = {1}",
                sourceValue.GetType().FullName, targetType.FullName));
            e.Value = (int)Math.Round((float)sourceValue * 100.0F, MidpointRounding.ToEven);
        }

        private void irisSizeRatioTrackBarValueBinding_Parse(object sender, ConvertEventArgs e)
        {
            Type sourceType = e.DesiredType;
            object targetValue = e.Value;
            Debug.Assert(sourceType == typeof(float) && targetValue is int,
                string.Format("SourceType = {0}, TargetType = {1}",
                sourceType.FullName, targetValue.GetType().FullName));
            e.Value = (float)((int)targetValue / 100.0F);
        }
    }
}

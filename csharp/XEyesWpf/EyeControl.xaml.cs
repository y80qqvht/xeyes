using System.Diagnostics;
using System.Windows;
using System.Windows.Controls;

namespace XEyesWpf
{
    /// <summary>
    /// EyeControl.xaml の相互作用ロジック
    /// </summary>
    public partial class EyeControl : UserControl
    {
        public EyeControl()
        {
            InitializeComponent();
        }

        protected override void OnRenderSizeChanged(SizeChangedInfo sizeInfo)
        {
            var model = GetModel();
            var newSize = sizeInfo.NewSize;
            if (sizeInfo.WidthChanged)
                model.EyeWidth = newSize.Width;
            if (sizeInfo.HeightChanged)
                model.EyeHeight = newSize.Height;

            base.OnRenderSizeChanged(sizeInfo);
        }

        private EyeControlModel GetModel()
        {
            var model = canvas.DataContext as EyeControlModel;
            Debug.Assert(model != null);
            return model;
        }

        public void LookAt(Point focus)
        {
            GetModel().LookAt(focus.X, focus.Y);
        }

        public double IrisSizeRatio
        {
            get { return (double)GetValue(IrisSizeRatioProperty); }
            set { SetValue(IrisSizeRatioProperty, value); }
        }

        public static readonly DependencyProperty IrisSizeRatioProperty = DependencyProperty.Register(
            "IrisSizeRatio", typeof(double), typeof(EyeControl),
            new FrameworkPropertyMetadata(
                0.4,
                FrameworkPropertyMetadataOptions.AffectsMeasure |
                FrameworkPropertyMetadataOptions.BindsTwoWayByDefault,
                IrisSizeRatioChanged),
            ValidateIrisSizeRatio);

        private static void IrisSizeRatioChanged(DependencyObject d, DependencyPropertyChangedEventArgs e)
        {
            var eyeControl = d as EyeControl;
            Debug.Assert(eyeControl != null,
                string.Format("Type of d = {0}", d.GetType().Name));
            eyeControl.GetModel().IrisSizeRatio = (double)e.NewValue;
        }

        private static bool ValidateIrisSizeRatio(object value)
        {
            Debug.Assert(value is double,
                string.Format("Type of value = {0}", value.GetType().Name));
            double v = (double)value;
            return 0.0 < v && v < 1.0;
        }
    }
}

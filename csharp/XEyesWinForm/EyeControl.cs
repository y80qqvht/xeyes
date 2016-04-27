using System;
using System.ComponentModel;
using System.Drawing;
using System.Windows.Forms;

namespace XEyesWinForm
{
    /// <summary>
    /// XEyes のひとつの目を描画するコントロールです。
    /// </summary>
    [DefaultBindingProperty("IrisSizeRatio")]
    [DefaultProperty("IrisSizeRatio")]
    [DefaultEvent("IrisSizeRatioChanged")]
    public class EyeControl : Control
    {
        #region デザイン時属性のオーバーライド

        /// <summary>
        /// <see cref="System.Windows.Forms.Control.TabStop"/>
        /// のデザイン時属性をオーバーライドします。
        /// </summary>
        [DefaultValue(false)]
        public new bool TabStop
        {
            get { return base.TabStop; }
            set { base.TabStop = value; }
        }

        /// <summary>
        /// <see cref="System.Windows.Forms.Control.Text"/>
        /// のデザイン時属性をオーバーライドします。
        /// </summary>
        [Bindable(false)]
        [Browsable(false)]
        [EditorBrowsable(EditorBrowsableState.Never)]
        public override string Text
        {
            get { return base.Text; }
            set { base.Text = value; }
        }

        #endregion

        /// <summary>
        /// コントロールの動作を定義するモデルです。
        /// </summary>
        private EyeControlModel _model = new EyeControlModel();

        /// <summary>
        /// コントロールの動作を定義するモデルを取得します。
        /// </summary>
        private EyeControlModel Model
        {
            get { return _model; }
        }

        /// <summary>
        /// XEyes のひとつの目を描画するコントロールを作成します。
        /// </summary>
        public EyeControl()
            : base()
        {
            DoubleBuffered = true;
            TabStop = false;
            Text = string.Empty;
        }

        /// <summary>
        /// <see cref="System.Windows.Forms.Control.Resize"/>
        /// イベントを発生させます。
        /// </summary>
        /// <param name="e">
        /// イベント データを格納している
        /// <see cref="System.EventArgs"/>。
        /// </param>
        protected override void OnResize(EventArgs e)
        {
            Model.EyeWidth = this.Width;
            Model.EyeHeight = this.Height;

            base.OnResize(e);
        }

        /// <summary>
        /// 目の描画を行ったあと、
        /// <see cref="System.Windows.Forms.Control.Paint"/>
        /// イベントを発生させます。
        /// </summary>
        /// <param name="pe">
        /// イベント データを格納している
        /// <see cref="System.Windows.Forms.PaintEventArgs"/>。
        /// </param>
        protected override void OnPaint(PaintEventArgs pe)
        {
            var g = pe.Graphics;
            g.FillEllipse(Brushes.White,
                0.0F, 0.0F, Model.EyeWidth, Model.EyeHeight);
            g.FillEllipse(Brushes.Black,
                Model.IrisX, Model.IrisY, Model.IrisWidth, Model.IrisHeight);

            base.OnPaint(pe);
        }

        /// <summary>
        /// 瞳が指定された位置を向くようにします。
        /// </summary>
        /// <param name="focus">視線の先を示す位置。</param>
        public void LookAt(Point focus)
        {
            Model.LookAt(focus.X, focus.Y);
            Invalidate();
        }

        /// <summary>
        /// コントロールのサイズに対する瞳のサイズの比率を取得または設定します。
        /// </summary>
        [Bindable(true)]
        [DefaultValue(0.4F)]
        [Description("コントロールのサイズに対する瞳のサイズの比率です。")]
        public float IrisSizeRatio
        {
            get
            {
                return Model.IrisSizeRatio;
            }
            set
            {
                if (Model.IrisSizeRatio != value)
                {
                    Model.IrisSizeRatio = value;
                    OnIrisSizeRatioChanged(EventArgs.Empty);
                    Invalidate();
                }
            }
        }

        /// <summary>
        /// <see cref="IrisSizeRatio"/> プロパティの値が変更されたときに発生します。
        /// </summary>
        [Description("IrisSizeRatio プロパティの値が変更されたときに発生します。")]
        public event EventHandler IrisSizeRatioChanged;

        /// <summary>
        /// <see cref="IrisSizeRatioChanged"/> イベントを発生させます。 
        /// </summary>
        /// <param name="e">
        /// イベント データを格納している
        /// <see cref="System.EventArgs"/>。
        /// </param>
        protected virtual void OnIrisSizeRatioChanged(EventArgs e)
        {
            var handler = IrisSizeRatioChanged;
            if (handler != null)
                handler(this, e);
        }
    }
}

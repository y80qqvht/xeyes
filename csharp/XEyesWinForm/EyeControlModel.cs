using System;
using System.ComponentModel;
using System.Diagnostics;
using System.Drawing;

namespace XEyesWinForm
{
    internal class EyeControlModel : INotifyPropertyChanged
    {
        internal EyeControlModel()
        {
        }

        private float _eyeWidth = 90.0F;

        public float EyeWidth
        {
            get
            {
                return _eyeWidth;
            }
            set
            {
                if (_eyeWidth != value)
                {
                    if (float.IsNaN(value) || value <= 0.0)
                        throw new ArgumentOutOfRangeException("value", value, string.Empty);
                    _eyeWidth = value;
                    NotifyPropertyChanged("EyeWidth");
                    NotifyPropertyChanged("IrisWidth");
                }
            }
        }

        private float _eyeHeight = 160.0F;

        public float EyeHeight
        {
            get
            {
                return _eyeHeight;
            }
            set
            {
                if (_eyeHeight != value)
                {
                    if (float.IsNaN(value) || value <= 0.0)
                        throw new ArgumentOutOfRangeException("value", value, string.Empty);
                    _eyeHeight = value;
                    NotifyPropertyChanged("EyeHeight");
                    NotifyPropertyChanged("IrisHeight");
                }
            }
        }

        private float _irisSizeRatio = 0.4F;

        public float IrisSizeRatio
        {
            get
            {
                return _irisSizeRatio;
            }
            set
            {
                if (_irisSizeRatio != value)
                {
                    if (float.IsNaN(value) || value <= 0.0F || value >= 1.0F)
                        throw new ArgumentOutOfRangeException("value", value, string.Empty);
                    _irisSizeRatio = value;
                    NotifyPropertyChanged("IrisSizeRatio");
                    NotifyPropertyChanged("IrisWidth");
                    NotifyPropertyChanged("IrisHeight");
                }
            }
        }

        public float IrisWidth
        {
            get { return EyeWidth * IrisSizeRatio; }
        }

        public float IrisHeight
        {
            get { return EyeHeight * IrisSizeRatio; }
        }

        private float _irisX;

        public float IrisX
        {
            get
            {
                return _irisX;
            }
            private set
            {
                if (_irisX != value)
                {
                    Debug.Assert(!float.IsNaN(value), "New IrisX is NaN");
                    _irisX = value;
                    NotifyPropertyChanged("IrisX");
                }
            }
        }

        private float _irisY;

        public float IrisY
        {
            get
            {
                return _irisY;
            }
            private set
            {
                if (_irisY != value)
                {
                    Debug.Assert(!float.IsNaN(value), "New IrisY is NaN");
                    _irisY = value;
                    NotifyPropertyChanged("IrisY");
                }
            }
        }

        public void LookAt(float focusX, float focusY)
        {
            if (float.IsNaN(focusX))
                throw new ArgumentOutOfRangeException("focusX", focusX, string.Empty);
            if (float.IsNaN(focusY))
                throw new ArgumentOutOfRangeException("focusY", focusY, string.Empty);
            var irisPosition = GetIrisPosition(focusX, focusY);
            IrisX = irisPosition.X;
            IrisY = irisPosition.Y;
        }

        private PointF GetIrisPosition(float focusX, float focusY)
        {
            double irisRadiusX = IrisWidth / 2.0;
            double irisRadiusY = IrisHeight / 2.0;
            double irisX = focusX - irisRadiusX;
            double irisY = focusY - irisRadiusY;

            double centerX = EyeWidth / 2.0;
            double centerY = EyeHeight / 2.0;
            double cFocusX = focusX - centerX;
            double cFocusY = focusY - centerY;

            if (cFocusX == 0.0)
            {
                double irisTopMin = 0.0;
                double irisTopMax = EyeHeight - IrisHeight;
                if (irisY < irisTopMin)
                    irisY = irisTopMin;
                else if (irisY > irisTopMax)
                    irisY = irisTopMax;
            }
            else
            {
                double tan = cFocusY / cFocusX;
                double a = centerX - irisRadiusX;
                double b = centerY - irisRadiusY;
                double tan2 = tan * tan;
                double a2 = a * a;
                double b2 = b * b;
                double cX2 = (a2 * b2) / (a2 * tan2 + b2);
                if (cX2 < cFocusX * cFocusX)
                {
                    double cX = Math.Sqrt(cX2);
                    if (cFocusX < 0.0)
                        cX = -cX;
                    double cY2 = cX2 * tan2;
                    double cY = Math.Sqrt(cY2);
                    if (cFocusY < 0.0)
                        cY = -cY;
                    irisX = cX + centerX - irisRadiusX;
                    irisY = cY + centerY - irisRadiusY;
                }
            }

            return new PointF((float)irisX, (float)irisY);
        }

        #region INotifyPropertyChanged メンバー

        public event PropertyChangedEventHandler PropertyChanged;

        protected void NotifyPropertyChanged(string propertyName)
        {
            CheckPropertyName(propertyName);
            var handler = PropertyChanged;
            if (handler != null)
                handler(this, new PropertyChangedEventArgs(propertyName));
        }

        [Conditional("DEBUG")]
        private void CheckPropertyName(string propertyName)
        {
            var type = GetType();
            var property = type.GetProperty(propertyName);
            Debug.Assert(property != null);
        }

        #endregion
    }
}

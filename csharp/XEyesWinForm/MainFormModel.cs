using System.ComponentModel;
using System.Diagnostics;
using System.Windows.Forms;
using XEyesWinForm.Configuration;

namespace XEyesWinForm
{
    internal class MainFormModel : INotifyPropertyChanged
    {
        private readonly XEyesWinFormConfiguration _config;

        internal MainFormModel(XEyesWinFormConfiguration config)
        {
            Debug.Assert(config != null, "config is null.");
            _config = config;
        }

        public int FormLeft
        {
            get
            {
                return _config.MainFormSettings.Left;
            }
            set
            {
                if (_config.MainFormSettings.Left != value)
                {
                    _config.MainFormSettings.Left = value;
                    NotifyPropertyChanged("FormLeft");
                }
            }
        }

        public int FormTop
        {
            get
            {
                return _config.MainFormSettings.Top;
            }
            set
            {
                if (_config.MainFormSettings.Top != value)
                {
                    _config.MainFormSettings.Top = value;
                    NotifyPropertyChanged("FormTop");
                }
            }
        }

        public int FormWidth
        {
            get
            {
                return _config.MainFormSettings.Width;
            }
            set
            {
                if (_config.MainFormSettings.Width != value)
                {
                    _config.MainFormSettings.Width = value;
                    NotifyPropertyChanged("FormWidth");
                }
            }
        }

        public int FormHeight
        {
            get
            {
                return _config.MainFormSettings.Height;
            }
            set
            {
                if (_config.MainFormSettings.Height != value)
                {
                    _config.MainFormSettings.Height = value;
                    NotifyPropertyChanged("FormHeight");
                }
            }
        }

        public FormWindowState WindowState
        {
            get
            {
                return _config.MainFormSettings.State;
            }
            set
            {
                if (_config.MainFormSettings.State != value)
                {
                    _config.MainFormSettings.State = value;
                    NotifyPropertyChanged("WindowState");
                }
            }
        }

        public bool TopMost
        {
            get
            {
                return _config.MainFormSettings.TopMost;
            }
            set
            {
                if (_config.MainFormSettings.TopMost != value)
                {
                    _config.MainFormSettings.TopMost = value;
                    NotifyPropertyChanged("TopMost");
                }
            }
        }

        public float IrisSizeRatio
        {
            get
            {
                return _config.XEyesSettings.IrisSizeRatio;
            }
            set
            {
                if (_config.XEyesSettings.IrisSizeRatio != value)
                {
                    _config.XEyesSettings.IrisSizeRatio = value;
                    NotifyPropertyChanged("IrisSizeRatio");
                }
            }
        }

        public bool SaveOnExit
        {
            get
            {
                return _config.XEyesSettings.SaveOnExit;
            }
            set
            {
                if (_config.XEyesSettings.SaveOnExit != value)
                {
                    _config.XEyesSettings.SaveOnExit = value;
                    NotifyPropertyChanged("SaveOnExit");
                }
            }
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

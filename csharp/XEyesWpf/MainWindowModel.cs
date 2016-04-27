using System.ComponentModel;
using System.Diagnostics;
using System.Windows;
using XEyesWpf.Configuration;

namespace XEyesWpf
{
    internal class MainWindowModel : INotifyPropertyChanged
    {
        private readonly XEyesWpfConfiguration _config;

        internal MainWindowModel(XEyesWpfConfiguration config)
        {
            Debug.Assert(config != null, "config is null.");
            _config = config;
        }

        public double WindowLeft
        {
            get
            {
                return _config.MainWindowSettings.Left;
            }
            set
            {
                _config.MainWindowSettings.Left = value;
                NotifyPropertyChanged("WindowLeft");
            }
        }

        public double WindowTop
        {
            get
            {
                return _config.MainWindowSettings.Top;
            }
            set
            {
                _config.MainWindowSettings.Top = value;
                NotifyPropertyChanged("WindowTop");
            }
        }

        public double WindowWidth
        {
            get
            {
                return _config.MainWindowSettings.Width;
            }
            set
            {
                _config.MainWindowSettings.Width = value;
                NotifyPropertyChanged("WindowWidth");
            }
        }

        public double WindowHeight
        {
            get
            {
                return _config.MainWindowSettings.Height;
            }
            set
            {
                _config.MainWindowSettings.Height = value;
                NotifyPropertyChanged("WindowHeight");
            }
        }

        public WindowState WindowState
        {
            get
            {
                return _config.MainWindowSettings.State;
            }
            set
            {
                _config.MainWindowSettings.State = value;
                NotifyPropertyChanged("WindowState");
            }
        }

        public bool Topmost
        {
            get
            {
                return _config.MainWindowSettings.Topmost;
            }
            set
            {
                _config.MainWindowSettings.Topmost = value;
                NotifyPropertyChanged("Topmost");
            }
        }

        public double IrisSizeRatio
        {
            get
            {
                return _config.XEyesSettings.IrisSizeRatio;
            }
            set
            {
                _config.XEyesSettings.IrisSizeRatio = value;
                NotifyPropertyChanged("IrisSizeRatio");
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
                _config.XEyesSettings.SaveOnExit = value;
                NotifyPropertyChanged("SaveOnExit");
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

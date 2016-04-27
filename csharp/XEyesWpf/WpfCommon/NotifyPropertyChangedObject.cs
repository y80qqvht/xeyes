using System;
using System.ComponentModel;

namespace WpfCommon
{
    public abstract class NotifyPropertyChangedObject : INotifyPropertyChanged
    {
        protected NotifyPropertyChangedObject()
        {
        }

        public event PropertyChangedEventHandler PropertyChanged;

        protected void RaisePropertyChanged(string propertyName)
        {
            var handler = PropertyChanged;
            if (handler != null)
                handler(this, new PropertyChangedEventArgs(propertyName));
        }
    }
}

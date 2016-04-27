using System.Configuration;
using System.Windows;

namespace XEyesWpf.Configuration
{
    public sealed class WindowSettingsSection : ConfigurationSection
    {
        public WindowSettingsSection()
            : base()
        {
        }

        private const string LeftPropertyName = "Left";

        [ConfigurationProperty(LeftPropertyName, DefaultValue = double.NaN)]
        public double Left
        {
            get { return (double)this[LeftPropertyName]; }
            set { this[LeftPropertyName] = value; }
        }

        private const string TopPropertyName = "Top";

        [ConfigurationProperty(TopPropertyName, DefaultValue = double.NaN)]
        public double Top
        {
            get { return (double)this[TopPropertyName]; }
            set { this[TopPropertyName] = value; }
        }

        private const string WidthPropertyName = "Width";

        [ConfigurationProperty(WidthPropertyName, DefaultValue = double.NaN)]
        public double Width
        {
            get { return (double)this[WidthPropertyName]; }
            set { this[WidthPropertyName] = value; }
        }

        private const string HeightPropertyName = "Height";

        [ConfigurationProperty(HeightPropertyName, DefaultValue = double.NaN)]
        public double Height
        {
            get { return (double)this[HeightPropertyName]; }
            set { this[HeightPropertyName] = value; }
        }

        private const string StatePropertyName = "State";

        [ConfigurationProperty(StatePropertyName, DefaultValue = WindowState.Normal)]
        public WindowState State
        {
            get { return (WindowState)this[StatePropertyName]; }
            set { this[StatePropertyName] = value; }
        }

        private const string TopmostPropertyName = "Topmost";

        [ConfigurationProperty(TopmostPropertyName, DefaultValue = false)]
        public bool Topmost
        {
            get { return (bool)this[TopmostPropertyName]; }
            set { this[TopmostPropertyName] = value; }
        }
    }
}

using System.Configuration;
using System.Windows.Forms;

namespace XEyesWinForm.Configuration
{
    public sealed class FormSettingsSection : ConfigurationSection
    {
        public FormSettingsSection()
            : base()
        {
        }

        private const string LeftPropertyName = "Left";

        [ConfigurationProperty(LeftPropertyName, DefaultValue = 0)]
        public int Left
        {
            get { return (int)this[LeftPropertyName]; }
            set { this[LeftPropertyName] = value; }
        }

        private const string TopPropertyName = "Top";

        [ConfigurationProperty(TopPropertyName, DefaultValue = 0)]
        public int Top
        {
            get { return (int)this[TopPropertyName]; }
            set { this[TopPropertyName] = value; }
        }

        private const string WidthPropertyName = "Width";

        [ConfigurationProperty(WidthPropertyName, DefaultValue = 300)]
        public int Width
        {
            get { return (int)this[WidthPropertyName]; }
            set { this[WidthPropertyName] = value; }
        }

        private const string HeightPropertyName = "Height";

        [ConfigurationProperty(HeightPropertyName, DefaultValue = 300)]
        public int Height
        {
            get { return (int)this[HeightPropertyName]; }
            set { this[HeightPropertyName] = value; }
        }

        private const string StatePropertyName = "State";

        [ConfigurationProperty(StatePropertyName, DefaultValue = FormWindowState.Normal)]
        public FormWindowState State
        {
            get { return (FormWindowState)this[StatePropertyName]; }
            set { this[StatePropertyName] = value; }
        }

        private const string TopMostPropertyName = "TopMost";

        [ConfigurationProperty(TopMostPropertyName, DefaultValue = false)]
        public bool TopMost
        {
            get { return (bool)this[TopMostPropertyName]; }
            set { this[TopMostPropertyName] = value; }
        }
    }
}

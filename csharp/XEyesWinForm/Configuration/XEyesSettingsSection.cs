using System.Configuration;

namespace XEyesWinForm.Configuration
{
    public sealed class XEyesSettingsSection : ConfigurationSection
    {
        public XEyesSettingsSection()
            : base()
        {
        }

        private const string IrisSizeRatioPropertyName = "IrisSizeRatio";

        [ConfigurationProperty(IrisSizeRatioPropertyName, DefaultValue = 0.4F)]
        [SingleValidator(MinValue = 0.0F, IncludeMinValue = false,
            MaxValue = 1.0F, IncludeMaxValue = false)]
        public float IrisSizeRatio
        {
            get { return (float)this[IrisSizeRatioPropertyName]; }
            set { this[IrisSizeRatioPropertyName] = value; }
        }

        private const string SaveOnExitPropertyName = "SaveOnExit";

        [ConfigurationProperty(SaveOnExitPropertyName, DefaultValue = true)]
        public bool SaveOnExit
        {
            get { return (bool)this[SaveOnExitPropertyName]; }
            set { this[SaveOnExitPropertyName] = value; }
        }
    }
}

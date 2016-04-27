using System.Configuration;

namespace XEyesWpf.Configuration
{
    public sealed class XEyesSettingsSection : ConfigurationSection
    {
        public XEyesSettingsSection()
            : base()
        {
        }

        private const string IrisSizeRatioPropertyName = "IrisSizeRatio";

        [ConfigurationProperty(IrisSizeRatioPropertyName, DefaultValue = 0.4)]
        [DoubleValidator(MinValue = 0.0, IncludeMinValue = false,
            MaxValue = 1.0, IncludeMaxValue = false)]
        public double IrisSizeRatio
        {
            get { return (double)this[IrisSizeRatioPropertyName]; }
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

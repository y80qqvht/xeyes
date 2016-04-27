using System.Configuration;
using System.Diagnostics;

namespace XEyesWinForm.Configuration
{
    public class XEyesWinFormConfiguration
    {
        // throws ConfigurationErrorsException
        public static XEyesWinFormConfiguration Open(string path)
        {
            var fileMap = new ExeConfigurationFileMap();
            fileMap.ExeConfigFilename = path;
            var configuration = ConfigurationManager.OpenMappedExeConfiguration(
                fileMap, ConfigurationUserLevel.None);
            return new XEyesWinFormConfiguration(configuration);
        }

        private XEyesWinFormConfiguration(System.Configuration.Configuration configuration)
        {
            Debug.Assert(configuration != null, "configuration is null");
            this._configuration = configuration;

            var xEyesSettings =
                configuration.GetSection(XEyesSettingsSectionName) as XEyesSettingsSection;
            if (xEyesSettings == null)
            {
                xEyesSettings = new XEyesSettingsSection();
                configuration.Sections.Add(XEyesSettingsSectionName, xEyesSettings);
            }
            this._xEyesSettings = xEyesSettings;

            var mainFormSettings =
                configuration.GetSection(MainFormSettingsSectionName) as FormSettingsSection;
            if (mainFormSettings == null)
            {
                mainFormSettings = new FormSettingsSection();
                configuration.Sections.Add(MainFormSettingsSectionName, mainFormSettings);
            }
            this._mainFormSettings = mainFormSettings;
        }

        private readonly System.Configuration.Configuration _configuration;

        private const string XEyesSettingsSectionName = "XEyesSettings";

        private readonly XEyesSettingsSection _xEyesSettings;

        public XEyesSettingsSection XEyesSettings
        {
            get { return _xEyesSettings; }
        }

        private const string MainFormSettingsSectionName = "MainFormSettings";

        private readonly FormSettingsSection _mainFormSettings;

        public FormSettingsSection MainFormSettings
        {
            get { return _mainFormSettings; }
        }

        public string FilePath
        {
            get { return _configuration.FilePath; }
        }

        public void Save()
        {
            _configuration.Save();
        }
    }
}

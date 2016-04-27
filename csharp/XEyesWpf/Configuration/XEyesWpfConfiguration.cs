using System.Configuration;
using System.Diagnostics;

namespace XEyesWpf.Configuration
{
    public class XEyesWpfConfiguration
    {
        // throws ConfigurationErrorsException
        public static XEyesWpfConfiguration Open(string path)
        {
            var fileMap = new ExeConfigurationFileMap();
            fileMap.ExeConfigFilename = path;
            var configuration = ConfigurationManager.OpenMappedExeConfiguration(
                fileMap, ConfigurationUserLevel.None);
            return new XEyesWpfConfiguration(configuration);
        }

        private XEyesWpfConfiguration(System.Configuration.Configuration configuration)
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

            var mainWindowSettings =
                configuration.GetSection(MainWindowSettingsSectionName) as WindowSettingsSection;
            if (mainWindowSettings == null)
            {
                mainWindowSettings = new WindowSettingsSection();
                configuration.Sections.Add(MainWindowSettingsSectionName, mainWindowSettings);
            }
            this._mainWindowSettings = mainWindowSettings;
        }

        private readonly System.Configuration.Configuration _configuration;

        private const string XEyesSettingsSectionName = "XEyesSettings";

        private readonly XEyesSettingsSection _xEyesSettings;

        public XEyesSettingsSection XEyesSettings
        {
            get { return _xEyesSettings; }
        }

        private const string MainWindowSettingsSectionName = "MainWindowSettings";

        private readonly WindowSettingsSection _mainWindowSettings;

        public WindowSettingsSection MainWindowSettings
        {
            get { return _mainWindowSettings; }
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

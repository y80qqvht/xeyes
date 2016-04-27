using System.ComponentModel;
using System.Windows.Forms;

namespace XEyesWinForm
{
    /// <summary>
    /// バインド可能な <see cref="System.Windows.Forms.ToolStripMenuItem"/> です。
    /// </summary>
    /// <seealso cref="http://social.msdn.microsoft.com/forums/en-US/winformsdatacontrols/thread/0b8cba1e-f7ce-4ab0-a45b-2093dc38afc8"/>
    public class BindableToolStripMenuItem : ToolStripMenuItem, IBindableComponent
    {
        public BindableToolStripMenuItem()
        {
            this._bindingContext = new BindingContext();
            this._dataBindings = new ControlBindingsCollection(this);
        }

        private BindingContext _bindingContext;

        [Browsable(false)]
        public BindingContext BindingContext
        {
            get { return _bindingContext; }
            set { _bindingContext = value; }
        }

        private readonly ControlBindingsCollection _dataBindings;

        [DesignerSerializationVisibility(DesignerSerializationVisibility.Content)]
        public ControlBindingsCollection DataBindings
        {
            get { return _dataBindings; }
        }
    }
}

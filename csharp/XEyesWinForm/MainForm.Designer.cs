namespace XEyesWinForm
{
    partial class MainForm
    {
        /// <summary>
        /// 必要なデザイナ変数です。
        /// </summary>
        private System.ComponentModel.IContainer components = null;

        /// <summary>
        /// 使用中のリソースをすべてクリーンアップします。
        /// </summary>
        /// <param name="disposing">マネージ リソースが破棄される場合 true、破棄されない場合は false です。</param>
        protected override void Dispose(bool disposing)
        {
            if (disposing && (components != null))
            {
                components.Dispose();
            }
            base.Dispose(disposing);
        }

        #region Windows フォーム デザイナで生成されたコード

        /// <summary>
        /// デザイナ サポートに必要なメソッドです。このメソッドの内容を
        /// コード エディタで変更しないでください。
        /// </summary>
        private void InitializeComponent()
        {
            this.components = new System.ComponentModel.Container();
            System.Windows.Forms.Timer mainTimer;
            System.Windows.Forms.TableLayoutPanel tableLayoutPanel;
            System.Windows.Forms.ToolStripSeparator toolStripSeparator1;
            System.Windows.Forms.ToolStripSeparator toolStripSeparator2;
            System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(typeof(MainForm));
            this.leftEye = new XEyesWinForm.EyeControl();
            this.modelBindingSource = new System.Windows.Forms.BindingSource(this.components);
            this.rightEye = new XEyesWinForm.EyeControl();
            this.mainContextMenu = new System.Windows.Forms.ContextMenuStrip(this.components);
            this.topMostMenuItem = new XEyesWinForm.BindableToolStripMenuItem();
            this.optionsMenuItem = new System.Windows.Forms.ToolStripMenuItem();
            this.saveMenuItem = new System.Windows.Forms.ToolStripMenuItem();
            this.exitMenuItem = new System.Windows.Forms.ToolStripMenuItem();
            mainTimer = new System.Windows.Forms.Timer(this.components);
            tableLayoutPanel = new System.Windows.Forms.TableLayoutPanel();
            toolStripSeparator1 = new System.Windows.Forms.ToolStripSeparator();
            toolStripSeparator2 = new System.Windows.Forms.ToolStripSeparator();
            tableLayoutPanel.SuspendLayout();
            ((System.ComponentModel.ISupportInitialize)(this.modelBindingSource)).BeginInit();
            this.mainContextMenu.SuspendLayout();
            this.SuspendLayout();
            // 
            // mainTimer
            // 
            mainTimer.Enabled = true;
            mainTimer.Interval = 50;
            mainTimer.Tick += new System.EventHandler(this.mainTimer_Tick);
            // 
            // tableLayoutPanel
            // 
            tableLayoutPanel.BackColor = System.Drawing.Color.FromArgb(((int)(((byte)(165)))), ((int)(((byte)(90)))), ((int)(((byte)(74)))));
            tableLayoutPanel.ColumnCount = 5;
            tableLayoutPanel.ColumnStyles.Add(new System.Windows.Forms.ColumnStyle(System.Windows.Forms.SizeType.Percent, 5F));
            tableLayoutPanel.ColumnStyles.Add(new System.Windows.Forms.ColumnStyle(System.Windows.Forms.SizeType.Percent, 40F));
            tableLayoutPanel.ColumnStyles.Add(new System.Windows.Forms.ColumnStyle(System.Windows.Forms.SizeType.Percent, 10F));
            tableLayoutPanel.ColumnStyles.Add(new System.Windows.Forms.ColumnStyle(System.Windows.Forms.SizeType.Percent, 40F));
            tableLayoutPanel.ColumnStyles.Add(new System.Windows.Forms.ColumnStyle(System.Windows.Forms.SizeType.Percent, 5F));
            tableLayoutPanel.Controls.Add(this.leftEye, 1, 1);
            tableLayoutPanel.Controls.Add(this.rightEye, 3, 1);
            tableLayoutPanel.Dock = System.Windows.Forms.DockStyle.Fill;
            tableLayoutPanel.GrowStyle = System.Windows.Forms.TableLayoutPanelGrowStyle.FixedSize;
            tableLayoutPanel.Location = new System.Drawing.Point(0, 0);
            tableLayoutPanel.Name = "tableLayoutPanel";
            tableLayoutPanel.RowCount = 3;
            tableLayoutPanel.RowStyles.Add(new System.Windows.Forms.RowStyle(System.Windows.Forms.SizeType.Percent, 5F));
            tableLayoutPanel.RowStyles.Add(new System.Windows.Forms.RowStyle(System.Windows.Forms.SizeType.Percent, 90F));
            tableLayoutPanel.RowStyles.Add(new System.Windows.Forms.RowStyle(System.Windows.Forms.SizeType.Percent, 5F));
            tableLayoutPanel.Size = new System.Drawing.Size(284, 262);
            tableLayoutPanel.TabIndex = 0;
            // 
            // leftEye
            // 
            this.leftEye.DataBindings.Add(new System.Windows.Forms.Binding("IrisSizeRatio", this.modelBindingSource, "IrisSizeRatio", true, System.Windows.Forms.DataSourceUpdateMode.Never));
            this.leftEye.Dock = System.Windows.Forms.DockStyle.Fill;
            this.leftEye.Location = new System.Drawing.Point(17, 16);
            this.leftEye.Name = "leftEye";
            this.leftEye.Size = new System.Drawing.Size(107, 229);
            this.leftEye.TabIndex = 0;
            // 
            // modelBindingSource
            // 
            this.modelBindingSource.DataSource = typeof(XEyesWinForm.MainFormModel);
            // 
            // rightEye
            // 
            this.rightEye.DataBindings.Add(new System.Windows.Forms.Binding("IrisSizeRatio", this.modelBindingSource, "IrisSizeRatio", true, System.Windows.Forms.DataSourceUpdateMode.Never));
            this.rightEye.Dock = System.Windows.Forms.DockStyle.Fill;
            this.rightEye.Location = new System.Drawing.Point(158, 16);
            this.rightEye.Name = "rightEye";
            this.rightEye.Size = new System.Drawing.Size(107, 229);
            this.rightEye.TabIndex = 1;
            // 
            // toolStripSeparator1
            // 
            toolStripSeparator1.Name = "toolStripSeparator1";
            toolStripSeparator1.Size = new System.Drawing.Size(247, 6);
            // 
            // toolStripSeparator2
            // 
            toolStripSeparator2.Name = "toolStripSeparator2";
            toolStripSeparator2.Size = new System.Drawing.Size(247, 6);
            // 
            // mainContextMenu
            // 
            this.mainContextMenu.Items.AddRange(new System.Windows.Forms.ToolStripItem[] {
            this.topMostMenuItem,
            this.optionsMenuItem,
            toolStripSeparator1,
            this.saveMenuItem,
            toolStripSeparator2,
            this.exitMenuItem});
            this.mainContextMenu.Name = "mainContextMenu";
            this.mainContextMenu.Size = new System.Drawing.Size(251, 104);
            // 
            // topMostMenuItem
            // 
            this.topMostMenuItem.CheckOnClick = true;
            this.topMostMenuItem.DataBindings.Add(new System.Windows.Forms.Binding("Checked", this.modelBindingSource, "TopMost", true, System.Windows.Forms.DataSourceUpdateMode.OnPropertyChanged));
            this.topMostMenuItem.Name = "topMostMenuItem";
            this.topMostMenuItem.Size = new System.Drawing.Size(250, 22);
            this.topMostMenuItem.Text = "最前面表示(&T)";
            // 
            // optionsMenuItem
            // 
            this.optionsMenuItem.Name = "optionsMenuItem";
            this.optionsMenuItem.Size = new System.Drawing.Size(250, 22);
            this.optionsMenuItem.Text = "オプション(&O)...";
            this.optionsMenuItem.Click += new System.EventHandler(this.optionsMenuItem_Click);
            // 
            // saveMenuItem
            // 
            this.saveMenuItem.Name = "saveMenuItem";
            this.saveMenuItem.Size = new System.Drawing.Size(250, 22);
            this.saveMenuItem.Text = "現在の設定をファイルへ保存(&S)";
            this.saveMenuItem.Click += new System.EventHandler(this.saveMenuItem_Click);
            // 
            // exitMenuItem
            // 
            this.exitMenuItem.Name = "exitMenuItem";
            this.exitMenuItem.Size = new System.Drawing.Size(250, 22);
            this.exitMenuItem.Text = "終了(&X)";
            this.exitMenuItem.Click += new System.EventHandler(this.exitMenuItem_Click);
            // 
            // MainForm
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 12F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(284, 262);
            this.ContextMenuStrip = this.mainContextMenu;
            this.Controls.Add(tableLayoutPanel);
            this.DataBindings.Add(new System.Windows.Forms.Binding("TopMost", this.modelBindingSource, "TopMost", true, System.Windows.Forms.DataSourceUpdateMode.OnPropertyChanged));
            this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
            this.MinimumSize = new System.Drawing.Size(180, 180);
            this.Name = "MainForm";
            this.StartPosition = System.Windows.Forms.FormStartPosition.Manual;
            tableLayoutPanel.ResumeLayout(false);
            ((System.ComponentModel.ISupportInitialize)(this.modelBindingSource)).EndInit();
            this.mainContextMenu.ResumeLayout(false);
            this.ResumeLayout(false);

        }

        #endregion

        private BindableToolStripMenuItem topMostMenuItem;
        private System.Windows.Forms.ToolStripMenuItem optionsMenuItem;
        private System.Windows.Forms.ToolStripMenuItem saveMenuItem;
        private System.Windows.Forms.ToolStripMenuItem exitMenuItem;
        private System.Windows.Forms.BindingSource modelBindingSource;
        private EyeControl leftEye;
        private EyeControl rightEye;
        private System.Windows.Forms.ContextMenuStrip mainContextMenu;

    }
}

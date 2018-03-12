/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GesMED.bsi.Interface;

import java.awt.CardLayout;
import java.awt.Color;
import java.util.List;

import javax.swing.table.DefaultTableModel;

import GesMED.bsi.Entidades.*;
import GesMED.bsi.Repositorios.PacienteRepositorio;

/**
 *
 * @author Leoncio Carioca
 */
public class ConsultaPacientes extends javax.swing.JFrame {

    private String StatusMenu = "";
    private PainelPrincipal pai = new PainelPrincipal();
    private PacienteRepositorio pr;
    /**
     * Creates new form ConsultaUsuarios
     */
    public ConsultaPacientes() {
        initComponents();
        StatusMenu = "PESQUISAR";
        pr = new PacienteRepositorio();
    }
    
    public ConsultaPacientes(PainelPrincipal painelP) {
        initComponents();
        StatusMenu = "PESQUISAR";
        pai = painelP;
        pr = new PacienteRepositorio();
    }
    
    
    public void preencherCamposPaciente(Paciente paciente){
    	
    	String CPF="", DataNascimento="", CEP="", Telefone="";
    	
    	//PREPARANDO OS DADOS DE CPF PARA O CPF FORMATADO.
    	CPF = paciente.getCPF();

    	String[] vetCPF = new String[4];
    	vetCPF[0] = CPF.substring(0, 3);
    	vetCPF[1] = CPF.substring(4, 7);
    	vetCPF[2] = CPF.substring(8,11);
    	vetCPF[3] = CPF.substring(12, CPF.length());

    	CPF = vetCPF[0]+vetCPF[1]+vetCPF[2]+vetCPF[3];
    	
    	//PREPARANDO OS DADOS DA DATA DE NASCIMENTO.
    	DataNascimento = paciente.getDataNasc();
    	String[] vetDataNasc = DataNascimento.split("/");
    	DataNascimento = vetDataNasc[0]+vetDataNasc[1]+vetDataNasc[2];
    	
    	//PREPARANDO OS DADOS DE TELEFONE PARA A FORMATAÇÃO 
    	Telefone = paciente.getTelefone().getTelefone();
    	//(68) XXXX-XXXX
    	Telefone = Telefone.substring(1, Telefone.length());
    	String[] vetTele = new String[2];
    	vetTele[0] = Telefone.substring(0, 1);
    	vetTele[1] = Telefone.substring(3, Telefone.length());
    	Telefone = vetTele[0]+vetTele[1];
    	vetTele = Telefone.split(" ");
    	Telefone = vetTele[0]+vetTele[1];
    	vetTele = Telefone.split("-");
    	Telefone = vetTele[0]+vetTele[1];
    	
    	//PREPARANDO O DADO DE CPF PARA O FORMATO 
    	CEP = paciente.getEndereco().getCEP();
    	String[] vetCEP = CEP.split("-");
    	CEP = vetCEP[0]+vetCEP[1];
    	
    	tfdNomePaciente.setText(paciente.getNome());
    	tfdCPFPaciente.setText(CPF);
    	tfdRGPaciente.setText(paciente.getRG());
    	tfdNascimento.setText(DataNascimento);
    	jcbTipoSanguineos.setSelectedItem(paciente.getTipoSangue());
    	tfdObservacao.setText(paciente.getObservacao());
    	tfdTelefonePaciente.setText(Telefone);
    	tfdNumeroPaciente.setText(paciente.getEndereco().getNumero());
    	tfdEndereco.setText(paciente.getEndereco().getRua());
    	tfdCEPPaciente.setText(CEP);
    	tdfBairro.setText(paciente.getEndereco().getBairro());
    	tfdPlanoSaude.setText(paciente.getPlanoSaude().getTitulo());
    	tfdDescricaoPS.setText(paciente.getPlanoSaude().getDescricao());
    
    }
    
    public void recebendoDadosPacientes() {
    	String Nome = tfdNomePaciente.getText();
    	String CPF = tfdCPFPaciente.getText();
    	String RG = tfdRGPaciente.getText();
    	String Nascimento = tfdNascimento.getText();
    	String TipoSanguineo = jcbTipoSanguineos.getSelectedItem().toString();
    	String Observacao = tfdObservacao.getText();
    	String Telefone = tfdTelefonePaciente.getText();
    	String Numero = tfdNumeroPaciente.getText();
    	String Rua = tfdEndereco.getText();
    	String CEP = tfdCEPPaciente.getText();
    	String Bairro = tdfBairro.getText();
    	String PlanoSaude = tfdPlanoSaude.getText();
    	String Descricao = tfdDescricaoPS.getText();
    	
    	
    	
    	PacienteRepositorio pr = new PacienteRepositorio();
    	pr.adicionar(getPaciente(Nome, CPF, RG, Nascimento, TipoSanguineo, Observacao, Telefone, Numero, Rua, CEP, Bairro, PlanoSaude, Descricao));
    	pr.encerrar();
    }
    
    public Paciente getPaciente(String Nome, String CPF, String RG, String Nascimento, String TipoSanguineo, 
    							String Observacao, String Telefone, String Numero, String Rua, String CEP, String Bairro, String PlanoSaude, String DescricaoPS) {
    	Paciente paciente = new Paciente();
    	paciente.setNome(Nome);
    	paciente.setCPF(CPF);
    	paciente.setRG(RG);
    	paciente.setDataNasc(Nascimento);
    	paciente.setTipoSangue(TipoSanguineo);
    	paciente.setObservacao(Observacao);
    	
    	Telefone phone = new Telefone();
    	phone.setTelefone(Telefone);
    	phone.setTipo("Residencial");
    	
    	paciente.setTelefone(phone);
    	
    	Endereco endereco = new Endereco();
    	endereco.setRua(Rua);
    	endereco.setBairro(Bairro);
    	endereco.setNumero(Numero);
    	endereco.setCEP(CEP);
    	
    	paciente.setEndereco(endereco);
    	
    	
    	return paciente;
    }
    
   public void BuscarPorNome(){
       PacienteRepositorio pr = new PacienteRepositorio();
       List<Paciente> listPacientes = pr.recuperarPacientesNome(tfd_Pesquisa.getText());
       CarregarListaTabela(listPacientes);
        
    }
   
   public void CarregarListaTabela(List<Paciente> listPaciente) {
	   
	   DefaultTableModel modelListPacientes = (DefaultTableModel)   tblListPacientes.getModel();
	   modelListPacientes.setNumRows(0);
	   
	   for(Paciente p : listPaciente) {
		   modelListPacientes.addRow(new Object[] {
				   p.getID(), p.getNome(), p
		   });
	   }
	   
   }
   	
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        bgTipoPesguisa = new javax.swing.ButtonGroup();
        bg = new javax.swing.JPanel();
        pLateral = new javax.swing.JPanel();
        pCardPesquisa = new javax.swing.JPanel();
        pPesquisar = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblListPacientes = new javax.swing.JTable();
        pOpcoes = new javax.swing.JPanel();
        lbPorTipo = new javax.swing.JLabel();
        lbBuscaTipoSanguineo = new javax.swing.JLabel();
        lblbBuscaBairro = new javax.swing.JLabel();
        rbBuscaNome = new javax.swing.JRadioButton();
        rbBuscaCPF = new javax.swing.JRadioButton();
        rbBuscaCodigo = new javax.swing.JRadioButton();
        jcbBairro = new javax.swing.JComboBox<>();
        jcbBuscaTipoSanguineos = new javax.swing.JComboBox<>();
        btn_Option = new javax.swing.JLabel();
        btn_Pesquisar = new javax.swing.JLabel();
        tfd_Pesquisa = new javax.swing.JTextField();
        btn_Buscar = new javax.swing.JButton();
        ImgPaciente = new javax.swing.JLabel();
        btn_Limpar = new javax.swing.JButton();
        btn_Editar = new javax.swing.JButton();
        pDetalhesUsuarios = new javax.swing.JPanel();
        lbNomeAtendente = new javax.swing.JLabel();
        tfdNomePaciente = new javax.swing.JTextField();
        lbRGAtendente = new javax.swing.JLabel();
        tfdRGPaciente = new javax.swing.JTextField();
        lbCPFAtendente = new javax.swing.JLabel();
        tfdCPFPaciente = new javax.swing.JTextField();
        lbNascimentoAtendente = new javax.swing.JLabel();
        tfdNascimento = new javax.swing.JTextField();
        lbObservacao = new javax.swing.JLabel();
        tfdObservacao = new javax.swing.JTextField();
        lbNascimentoPaciente2 = new javax.swing.JLabel();
        jcbTipoSanguineos = new javax.swing.JComboBox<>();
        lbTelefonePaciente = new javax.swing.JLabel();
        tfdTelefonePaciente = new javax.swing.JTextField();
        lbEnderecoPaciente = new javax.swing.JLabel();
        tfdEndereco = new javax.swing.JTextField();
        lbBairroPaciente = new javax.swing.JLabel();
        tdfBairro = new javax.swing.JTextField();
        lbPlanoSaude = new javax.swing.JLabel();
        tfdPlanoSaude = new javax.swing.JTextField();
        tfdCEPPaciente = new javax.swing.JTextField();
        lbCEPPaciente = new javax.swing.JLabel();
        lbNumeroPaciente = new javax.swing.JLabel();
        tfdNumeroPaciente = new javax.swing.JTextField();
        lbTitloJanelaDescricao = new javax.swing.JLabel();
        tfdDescricaoPS = new javax.swing.JTextField();
        lbDescricaoPS = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        lbDadosPessoais = new javax.swing.JLabel();
        jSeparator2 = new javax.swing.JSeparator();
        btn_Salvar = new javax.swing.JButton();
        btn_Excluir = new javax.swing.JButton();
        btn_Fechar = new javax.swing.JButton();

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        bg.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        pLateral.setBackground(new java.awt.Color(102, 204, 255));
        pLateral.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        pCardPesquisa.setBackground(new java.awt.Color(0, 204, 204));
        pCardPesquisa.setLayout(new java.awt.CardLayout());

        pPesquisar.setBackground(new java.awt.Color(0, 204, 204));

        tblListPacientes.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "COD", "NOME"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.Object.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jScrollPane1.setViewportView(tblListPacientes);
        if (tblListPacientes.getColumnModel().getColumnCount() > 0) {
            tblListPacientes.getColumnModel().getColumn(0).setMinWidth(30);
            tblListPacientes.getColumnModel().getColumn(0).setPreferredWidth(30);
            tblListPacientes.getColumnModel().getColumn(0).setMaxWidth(40);
            tblListPacientes.getColumnModel().getColumn(1).setMinWidth(200);
            tblListPacientes.getColumnModel().getColumn(1).setPreferredWidth(180);
            tblListPacientes.getColumnModel().getColumn(1).setMaxWidth(250);
        }

        javax.swing.GroupLayout pPesquisarLayout = new javax.swing.GroupLayout(pPesquisar);
        pPesquisar.setLayout(pPesquisarLayout);
        pPesquisarLayout.setHorizontalGroup(
            pPesquisarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 310, Short.MAX_VALUE)
        );
        pPesquisarLayout.setVerticalGroup(
            pPesquisarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pPesquisarLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 416, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pCardPesquisa.add(pPesquisar, "cardPesquisar");

        pOpcoes.setBackground(new java.awt.Color(0, 204, 204));

        lbPorTipo.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        lbPorTipo.setText("Por:");

        lbBuscaTipoSanguineo.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        lbBuscaTipoSanguineo.setText("Tipo Sanguíneo:");

        lblbBuscaBairro.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        lblbBuscaBairro.setText("Bairro:");

        bgTipoPesguisa.add(rbBuscaNome);
        rbBuscaNome.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        rbBuscaNome.setText("NOME");

        bgTipoPesguisa.add(rbBuscaCPF);
        rbBuscaCPF.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        rbBuscaCPF.setText("CPF");
        rbBuscaCPF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbBuscaCPFActionPerformed(evt);
            }
        });

        bgTipoPesguisa.add(rbBuscaCodigo);
        rbBuscaCodigo.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        rbBuscaCodigo.setText("CÓDIGO");

        jcbBairro.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jcbBairro.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Selecione", "Bairro 1", "Bairro 2", "Bairro 3", "Bairro N" }));

        jcbBuscaTipoSanguineos.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jcbBuscaTipoSanguineos.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Selecione", "A+", "A-", "B+,", "B-", "AB+", "AB-", "O+", "O-" }));

        javax.swing.GroupLayout pOpcoesLayout = new javax.swing.GroupLayout(pOpcoes);
        pOpcoes.setLayout(pOpcoesLayout);
        pOpcoesLayout.setHorizontalGroup(
            pOpcoesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pOpcoesLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(pOpcoesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pOpcoesLayout.createSequentialGroup()
                        .addComponent(lblbBuscaBairro)
                        .addGap(18, 18, 18)
                        .addComponent(jcbBairro, javax.swing.GroupLayout.PREFERRED_SIZE, 176, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pOpcoesLayout.createSequentialGroup()
                        .addComponent(lbBuscaTipoSanguineo)
                        .addGap(18, 18, 18)
                        .addComponent(jcbBuscaTipoSanguineos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pOpcoesLayout.createSequentialGroup()
                        .addComponent(lbPorTipo)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(rbBuscaCodigo)
                        .addGap(16, 16, 16)
                        .addComponent(rbBuscaNome)
                        .addGap(18, 18, 18)
                        .addComponent(rbBuscaCPF)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pOpcoesLayout.setVerticalGroup(
            pOpcoesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pOpcoesLayout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addGroup(pOpcoesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbPorTipo)
                    .addComponent(rbBuscaNome)
                    .addComponent(rbBuscaCPF)
                    .addComponent(rbBuscaCodigo))
                .addGap(30, 30, 30)
                .addGroup(pOpcoesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblbBuscaBairro)
                    .addComponent(jcbBairro, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(26, 26, 26)
                .addGroup(pOpcoesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbBuscaTipoSanguineo)
                    .addComponent(jcbBuscaTipoSanguineos, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(179, Short.MAX_VALUE))
        );

        pCardPesquisa.add(pOpcoes, "cardOpcoes");

        pLateral.add(pCardPesquisa, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 169, 310, 360));

        btn_Option.setBackground(new java.awt.Color(0, 230, 255));
        btn_Option.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        btn_Option.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btn_Option.setText("OPÇÕES DE BUSCA");
        btn_Option.setOpaque(true);
        btn_Option.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn_OptionMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btn_OptionMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btn_OptionMouseExited(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                btn_OptionMousePressed(evt);
            }
        });
        pLateral.add(btn_Option, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 140, 160, 30));

        btn_Pesquisar.setBackground(new java.awt.Color(0, 204, 204));
        btn_Pesquisar.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        btn_Pesquisar.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btn_Pesquisar.setText("PESQUISA");
        btn_Pesquisar.setOpaque(true);
        btn_Pesquisar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn_PesquisarMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btn_PesquisarMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btn_PesquisarMouseExited(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                btn_PesquisarMousePressed(evt);
            }
        });
        pLateral.add(btn_Pesquisar, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 140, 111, 30));
        pLateral.add(tfd_Pesquisa, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 90, 250, 40));

        btn_Buscar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ufac/bsi/images/Search_40px.png"))); // NOI18N
        btn_Buscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_BuscarActionPerformed(evt);
            }
        });
        pLateral.add(btn_Buscar, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 90, 50, 40));

        ImgPaciente.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ufac/bsi/images/Pacientes_60px.png"))); // NOI18N
        pLateral.add(ImgPaciente, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 20, 70, 50));

        btn_Limpar.setBackground(new java.awt.Color(204, 204, 204));
        btn_Limpar.setText("LIMPAR");
        btn_Limpar.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btn_Limpar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_LimparActionPerformed(evt);
            }
        });
        pLateral.add(btn_Limpar, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 540, 70, 30));

        btn_Editar.setBackground(new java.awt.Color(204, 204, 204));
        btn_Editar.setText("EDITAR");
        btn_Editar.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btn_Editar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_EditarActionPerformed(evt);
            }
        });
        pLateral.add(btn_Editar, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 540, 70, 30));

        bg.add(pLateral, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 310, 580));

        pDetalhesUsuarios.setBackground(new java.awt.Color(255, 255, 255));

        lbNomeAtendente.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lbNomeAtendente.setText("NOME:");

        lbRGAtendente.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lbRGAtendente.setText("RG:");

        lbCPFAtendente.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lbCPFAtendente.setText("CPF:");

        lbNascimentoAtendente.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lbNascimentoAtendente.setText("Nascimento:");

        lbObservacao.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lbObservacao.setText("Observação:");

        lbNascimentoPaciente2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lbNascimentoPaciente2.setText("Tipo Sanguíneo:");

        jcbTipoSanguineos.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jcbTipoSanguineos.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "A+", "A-", "B+,", "B-", "AB+", "AB-", "O+", "O-" }));

        lbTelefonePaciente.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lbTelefonePaciente.setText("Telefone:");

        lbEnderecoPaciente.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lbEnderecoPaciente.setText("Endereço:");

        lbBairroPaciente.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lbBairroPaciente.setText("Bairro:");

        lbPlanoSaude.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lbPlanoSaude.setText("Plano de Saúde:");

        tfdPlanoSaude.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tfdPlanoSaudeActionPerformed(evt);
            }
        });

        lbCEPPaciente.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lbCEPPaciente.setText("CEP:");

        lbNumeroPaciente.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lbNumeroPaciente.setText("Número:");

        lbTitloJanelaDescricao.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        lbTitloJanelaDescricao.setText("Dados do Paciente");

        lbDescricaoPS.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lbDescricaoPS.setText("Descrição:");

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel1.setText("Outras Informações");

        lbDadosPessoais.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lbDadosPessoais.setText("Dados Pessoais");

        btn_Salvar.setBackground(new java.awt.Color(0, 153, 153));
        btn_Salvar.setText("SALVAR");
        btn_Salvar.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btn_Salvar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_SalvarActionPerformed(evt);
            }
        });

        btn_Excluir.setBackground(new java.awt.Color(0, 153, 153));
        btn_Excluir.setText("EXCLUIR");
        btn_Excluir.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btn_Excluir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_ExcluirActionPerformed(evt);
            }
        });

        btn_Fechar.setBackground(new java.awt.Color(0, 153, 153));
        btn_Fechar.setText("FECHAR");
        btn_Fechar.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btn_Fechar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_FecharActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pDetalhesUsuariosLayout = new javax.swing.GroupLayout(pDetalhesUsuarios);
        pDetalhesUsuarios.setLayout(pDetalhesUsuariosLayout);
        pDetalhesUsuariosLayout.setHorizontalGroup(
            pDetalhesUsuariosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pDetalhesUsuariosLayout.createSequentialGroup()
                .addGroup(pDetalhesUsuariosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pDetalhesUsuariosLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(lbPlanoSaude, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(tfdPlanoSaude)
                        .addGap(22, 22, 22)
                        .addGroup(pDetalhesUsuariosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lbDescricaoPS)
                            .addComponent(tfdDescricaoPS, javax.swing.GroupLayout.PREFERRED_SIZE, 246, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(pDetalhesUsuariosLayout.createSequentialGroup()
                        .addGroup(pDetalhesUsuariosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(pDetalhesUsuariosLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jLabel1)
                                .addGap(18, 18, 18)
                                .addComponent(jSeparator1))
                            .addGroup(pDetalhesUsuariosLayout.createSequentialGroup()
                                .addGap(22, 22, 22)
                                .addGroup(pDetalhesUsuariosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(pDetalhesUsuariosLayout.createSequentialGroup()
                                        .addComponent(lbNascimentoAtendente)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(tfdNascimento, javax.swing.GroupLayout.PREFERRED_SIZE, 263, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(lbRGAtendente)
                                        .addGap(18, 18, 18)
                                        .addComponent(tfdRGPaciente, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(pDetalhesUsuariosLayout.createSequentialGroup()
                                        .addGroup(pDetalhesUsuariosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(lbNomeAtendente)
                                            .addComponent(lbCPFAtendente))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addGroup(pDetalhesUsuariosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(tfdCPFPaciente, javax.swing.GroupLayout.PREFERRED_SIZE, 263, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(tfdNomePaciente, javax.swing.GroupLayout.DEFAULT_SIZE, 606, Short.MAX_VALUE)))))
                            .addGroup(pDetalhesUsuariosLayout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(pDetalhesUsuariosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(lbTelefonePaciente)
                                    .addComponent(lbEnderecoPaciente)
                                    .addComponent(lbBairroPaciente))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(pDetalhesUsuariosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(pDetalhesUsuariosLayout.createSequentialGroup()
                                        .addGroup(pDetalhesUsuariosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(tfdEndereco, javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, pDetalhesUsuariosLayout.createSequentialGroup()
                                                .addComponent(tfdTelefonePaciente, javax.swing.GroupLayout.PREFERRED_SIZE, 266, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(0, 0, Short.MAX_VALUE)))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(pDetalhesUsuariosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addGroup(pDetalhesUsuariosLayout.createSequentialGroup()
                                                .addComponent(lbCEPPaciente)
                                                .addGap(18, 18, 18)
                                                .addComponent(tfdCEPPaciente, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addGroup(pDetalhesUsuariosLayout.createSequentialGroup()
                                                .addComponent(lbNumeroPaciente)
                                                .addGap(18, 18, 18)
                                                .addComponent(tfdNumeroPaciente, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                    .addGroup(pDetalhesUsuariosLayout.createSequentialGroup()
                                        .addComponent(tdfBairro, javax.swing.GroupLayout.PREFERRED_SIZE, 269, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(0, 0, Short.MAX_VALUE))))
                            .addGroup(pDetalhesUsuariosLayout.createSequentialGroup()
                                .addGap(239, 239, 239)
                                .addComponent(lbTitloJanelaDescricao))
                            .addGroup(pDetalhesUsuariosLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(lbDadosPessoais)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jSeparator2))
                            .addGroup(pDetalhesUsuariosLayout.createSequentialGroup()
                                .addGap(26, 26, 26)
                                .addComponent(lbNascimentoPaciente2)
                                .addGap(18, 18, 18)
                                .addComponent(jcbTipoSanguineos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(lbObservacao)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(tfdObservacao)))
                        .addGap(0, 1, Short.MAX_VALUE)))
                .addContainerGap())
            .addGroup(pDetalhesUsuariosLayout.createSequentialGroup()
                .addGap(137, 137, 137)
                .addComponent(btn_Salvar, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20)
                .addComponent(btn_Excluir, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(26, 26, 26)
                .addComponent(btn_Fechar, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pDetalhesUsuariosLayout.setVerticalGroup(
            pDetalhesUsuariosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pDetalhesUsuariosLayout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(lbTitloJanelaDescricao)
                .addGap(19, 19, 19)
                .addGroup(pDetalhesUsuariosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lbDadosPessoais)
                    .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(pDetalhesUsuariosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbNomeAtendente)
                    .addComponent(tfdNomePaciente, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(pDetalhesUsuariosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbCPFAtendente)
                    .addComponent(tfdCPFPaciente, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbRGAtendente)
                    .addComponent(tfdRGPaciente, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(pDetalhesUsuariosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbNascimentoAtendente)
                    .addComponent(tfdNascimento, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(pDetalhesUsuariosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pDetalhesUsuariosLayout.createSequentialGroup()
                        .addGroup(pDetalhesUsuariosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jcbTipoSanguineos)
                            .addComponent(lbNascimentoPaciente2, javax.swing.GroupLayout.DEFAULT_SIZE, 33, Short.MAX_VALUE)
                            .addComponent(lbObservacao))
                        .addGap(29, 29, 29))
                    .addGroup(pDetalhesUsuariosLayout.createSequentialGroup()
                        .addGap(2, 2, 2)
                        .addComponent(tfdObservacao, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, Short.MAX_VALUE)))
                .addGroup(pDetalhesUsuariosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel1)
                    .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(pDetalhesUsuariosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pDetalhesUsuariosLayout.createSequentialGroup()
                        .addGroup(pDetalhesUsuariosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lbTelefonePaciente)
                            .addComponent(tfdTelefonePaciente, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(pDetalhesUsuariosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lbEnderecoPaciente)
                            .addComponent(tfdEndereco, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pDetalhesUsuariosLayout.createSequentialGroup()
                        .addGroup(pDetalhesUsuariosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lbNumeroPaciente)
                            .addComponent(tfdNumeroPaciente, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(pDetalhesUsuariosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lbCEPPaciente)
                            .addComponent(tfdCEPPaciente, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGroup(pDetalhesUsuariosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pDetalhesUsuariosLayout.createSequentialGroup()
                        .addGap(59, 59, 59)
                        .addGroup(pDetalhesUsuariosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lbPlanoSaude)
                            .addComponent(tfdPlanoSaude, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(pDetalhesUsuariosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(lbDescricaoPS)
                        .addComponent(tdfBairro, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(lbBairroPaciente))
                    .addGroup(pDetalhesUsuariosLayout.createSequentialGroup()
                        .addGap(21, 21, 21)
                        .addComponent(tfdDescricaoPS, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(pDetalhesUsuariosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btn_Salvar, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(pDetalhesUsuariosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btn_Excluir, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btn_Fechar, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );

        bg.add(pDetalhesUsuarios, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 0, 700, 580));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(bg, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(bg, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btn_PesquisarMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_PesquisarMouseEntered
        if(!"PESQUISAR".equals(StatusMenu))
        btn_Pesquisar.setBackground(new Color(0,204,153));
    }//GEN-LAST:event_btn_PesquisarMouseEntered

    private void btn_OptionMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_OptionMouseEntered
        if(!"OPTION".equals(StatusMenu))
        btn_Option.setBackground(new Color(0,204,153));
    }//GEN-LAST:event_btn_OptionMouseEntered

    private void btn_PesquisarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_PesquisarMouseClicked
        btn_Pesquisar.setBackground(new Color(0,204,204));
        btn_Option.setBackground(new Color(0,230,255));
        StatusMenu = "PESQUISAR";
        CardLayout cl = (CardLayout) pCardPesquisa.getLayout();
        cl.show(pCardPesquisa, "cardPesquisar");
    }//GEN-LAST:event_btn_PesquisarMouseClicked

    private void btn_OptionMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_OptionMouseClicked
        btn_Option.setBackground(new Color(0,204,204));
        btn_Pesquisar.setBackground(new Color(0,230,255));
        StatusMenu = "OPTION";
        CardLayout cl = (CardLayout) pCardPesquisa.getLayout();
        cl.show(pCardPesquisa, "cardOpcoes");
    }//GEN-LAST:event_btn_OptionMouseClicked

    private void btn_PesquisarMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_PesquisarMouseExited
        if(!"PESQUISAR".equals(StatusMenu))
        btn_Pesquisar.setBackground(new Color(0,230,255));
    }//GEN-LAST:event_btn_PesquisarMouseExited

    private void btn_OptionMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_OptionMouseExited
        if(!"OPTION".equals(StatusMenu))
        btn_Option.setBackground(new Color(0,230,255));
    }//GEN-LAST:event_btn_OptionMouseExited

    private void btn_PesquisarMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_PesquisarMousePressed
        btn_Pesquisar.setBackground(new Color(0,153,153));
    }//GEN-LAST:event_btn_PesquisarMousePressed

    private void btn_OptionMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_OptionMousePressed
        btn_Option.setBackground(new Color(0,153,153));
    }//GEN-LAST:event_btn_OptionMousePressed

    private void rbBuscaCPFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbBuscaCPFActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_rbBuscaCPFActionPerformed

    private void btn_LimparActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_LimparActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btn_LimparActionPerformed

    private void btn_EditarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_EditarActionPerformed
        int ID = (Integer) (tblListPacientes.getValueAt(tblListPacientes.getSelectedRow(), 0));
        Paciente paciente = pr.recuperar(ID);
//        System.out.println("Paciente Nome: " + paciente.getNome() + " ID após converter : "+ID);
        preencherCamposPaciente(paciente);
    }//GEN-LAST:event_btn_EditarActionPerformed

    private void btn_SalvarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_SalvarActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btn_SalvarActionPerformed

    private void btn_ExcluirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_ExcluirActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btn_ExcluirActionPerformed

    private void tfdPlanoSaudeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tfdPlanoSaudeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tfdPlanoSaudeActionPerformed

    private void btn_FecharActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_FecharActionPerformed
        pai.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_btn_FecharActionPerformed

    private void btn_BuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_BuscarActionPerformed
            BuscarPorNome();
    }//GEN-LAST:event_btn_BuscarActionPerformed
    
 
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(ConsultaPacientes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ConsultaPacientes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ConsultaPacientes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ConsultaPacientes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ConsultaPacientes().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel ImgPaciente;
    private javax.swing.JPanel bg;
    private javax.swing.ButtonGroup bgTipoPesguisa;
    private javax.swing.JButton btn_Buscar;
    private javax.swing.JButton btn_Editar;
    private javax.swing.JButton btn_Excluir;
    private javax.swing.JButton btn_Fechar;
    private javax.swing.JButton btn_Limpar;
    private javax.swing.JLabel btn_Option;
    private javax.swing.JLabel btn_Pesquisar;
    private javax.swing.JButton btn_Salvar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JComboBox<String> jcbBairro;
    private javax.swing.JComboBox<String> jcbBuscaTipoSanguineos;
    private javax.swing.JComboBox<String> jcbTipoSanguineos;
    private javax.swing.JLabel lbBairroPaciente;
    private javax.swing.JLabel lbBuscaTipoSanguineo;
    private javax.swing.JLabel lbCEPPaciente;
    private javax.swing.JLabel lbCPFAtendente;
    private javax.swing.JLabel lbDadosPessoais;
    private javax.swing.JLabel lbDescricaoPS;
    private javax.swing.JLabel lbEnderecoPaciente;
    private javax.swing.JLabel lbNascimentoAtendente;
    private javax.swing.JLabel lbNascimentoPaciente2;
    private javax.swing.JLabel lbNomeAtendente;
    private javax.swing.JLabel lbNumeroPaciente;
    private javax.swing.JLabel lbObservacao;
    private javax.swing.JLabel lbPlanoSaude;
    private javax.swing.JLabel lbPorTipo;
    private javax.swing.JLabel lbRGAtendente;
    private javax.swing.JLabel lbTelefonePaciente;
    private javax.swing.JLabel lbTitloJanelaDescricao;
    private javax.swing.JLabel lblbBuscaBairro;
    private javax.swing.JPanel pCardPesquisa;
    private javax.swing.JPanel pDetalhesUsuarios;
    private javax.swing.JPanel pLateral;
    private javax.swing.JPanel pOpcoes;
    private javax.swing.JPanel pPesquisar;
    private javax.swing.JRadioButton rbBuscaCPF;
    private javax.swing.JRadioButton rbBuscaCodigo;
    private javax.swing.JRadioButton rbBuscaNome;
    private javax.swing.JTable tblListPacientes;
    private javax.swing.JTextField tdfBairro;
    private javax.swing.JTextField tfdCEPPaciente;
    private javax.swing.JTextField tfdCPFPaciente;
    private javax.swing.JTextField tfdDescricaoPS;
    private javax.swing.JTextField tfdEndereco;
    private javax.swing.JTextField tfdNascimento;
    private javax.swing.JTextField tfdNomePaciente;
    private javax.swing.JTextField tfdNumeroPaciente;
    private javax.swing.JTextField tfdObservacao;
    private javax.swing.JTextField tfdPlanoSaude;
    private javax.swing.JTextField tfdRGPaciente;
    private javax.swing.JTextField tfdTelefonePaciente;
    private javax.swing.JTextField tfd_Pesquisa;
    // End of variables declaration//GEN-END:variables
}

package br.com.compilador.image;

import javax.swing.ImageIcon;

public final class MasterImage extends ImageIcon {
	private static final long serialVersionUID = -1243931450819789591L;

	// 13x13
	public static final MasterImage novo = LoadImage("novo.png");
	public static final MasterImage abrir = LoadImage("pasta_criar.png");
	public static final MasterImage salvar = LoadImage("salvar.png");
	public static final MasterImage monitor = LoadImage("monitor.png");
	public static final MasterImage executar = LoadImage("play.png");
	public static final MasterImage debugOff = LoadImage("debugOff.png");
	public static final MasterImage debugOn = LoadImage("debugOn.png");
	public static final MasterImage resumeProx = LoadImage("avancar.png");
	public static final MasterImage parar = LoadImage("cancelar.png");
	public static final MasterImage sair = LoadImage("sair.png");	
	
	private MasterImage(final String titulo) {
		super(MasterImage.class.getResource(titulo));
	}

	/**
	 * Carrega a imagem de acordo com o texto passado por parametro.
	 * 
	 * @param tituloIcone
	 * @return ImageIcon
	 */

	public static MasterImage LoadImage(final String tituloIcone) {
		return new MasterImage(tituloIcone);
	}
}

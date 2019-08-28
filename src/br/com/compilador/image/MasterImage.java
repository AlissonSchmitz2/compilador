package br.com.compilador.image;

import javax.swing.ImageIcon;

public final class MasterImage extends ImageIcon{
	private static final long serialVersionUID = -1243931450819789591L;

	//22x22
	public static final MasterImage
	pasta_22x22 = LoadImage("22x22/pasta_criar.png");
	
	public static final MasterImage
	monitor = LoadImage("monitor.png");
	
	public static final MasterImage
	executar = LoadImage("play.png");

	public static final MasterImage
	sair = LoadImage("sair.png");
	
	private MasterImage(final String titulo) {
		super(MasterImage.class.getResource(titulo));
	}
	
	/**
	 * Carrega a imagem de acordo com o texto passado por
	 * parametro.
	 * @param tituloIcone
	 * @return ImageIcon
	 */
	
	public static MasterImage LoadImage(final String tituloIcone) {
		return new MasterImage(tituloIcone);
	}
}


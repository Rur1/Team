package net.pddin.agc;

/**
 * AGC�C���^�[�t�F�[�X�uIAGC�v
 *
 * @version 1.0 H25/06/26 @author HARADA
 */
public interface IAGC {
	public void init(); // �������������\�b�h

	public void update(); // �X�V�������\�b�h

	public void draw(Graphics g); // �`�揈�����\�b�h

	public void finish(); // �I���������\�b�h
}

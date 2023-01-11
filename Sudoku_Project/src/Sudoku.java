import java.util.Scanner;

import javax.swing.JButton;

public class Sudoku
{
    int[] mat[];
    int N; // �� ��
    int SRN; // ��Ʈ(N)
    int K;
    int [][] Enter_answer;
    boolean [][] Check_sudoku;
    int c,r,e;
    int count = 0;
    int sum=0;
    Scanner sc = new Scanner(System.in);
    // ������
    Sudoku(int N, int K)
    {
        this.N = N;
        this.K = K;
        Double SRNd = Math.sqrt(N);
        SRN = SRNd.intValue();
        
        Check_sudoku = new boolean[N][N];
        Enter_answer = new int[N][N];
        mat = new int[N][N];
    }
 
    // ������ ������
    public void fillValues()
    {
        fillDiagonal();
        
        // ������ ��� ä���
        fillRemaining(0, SRN);
        
        // �� �ű��
        fillCarry();
        
        // ���� ����
        removeKDigits();
    }
    
    void fillDiagonal()
    {
 
        for (int i = 0; i<N; i=i+SRN)
            fillBox(i, i);
    }
    
    void fillCarry() {
    	for (int i=0;i<N;i++) {
        	for(int j=0;j<N;j++) {
        		Enter_answer[i][j] = mat[i][j];
        	}
        }
    }
 
    // 3x3�� ���� ���� �� false ��ȯ
    boolean unUsedInBox(int rowStart, int colStart, int num)
    {
        for (int i = 0; i<SRN; i++)
            for (int j = 0; j<SRN; j++)
                if (mat[rowStart+i][colStart+j]==num)
                    return false;
 
        return true;
    }
 
    // 3x3 ��� �� �Է�
    void fillBox(int row,int col)
    {
        int num;
        for (int i=0; i<SRN; i++)
        {
            for (int j=0; j<SRN; j++)
            {
                do
                {
                    num = randomGenerator(N);
                }
                while (!unUsedInBox(row, col, num));
 
                mat[row+i][col+j] = num;
            }
        }
        
        

    }
 
    // ���� �� ����
    int randomGenerator(int num)
    {
        return (int) Math.floor((Math.random()*num+1));
    }
 
    // ���� �־ �Ǵ��� Ȯ��
    boolean CheckIfSafe(int i,int j,int num)
    {
        return (unUsedInRow(i, num) &&
                unUsedInCol(j, num) &&
                unUsedInBox(i-i%SRN, j-j%SRN, num));
    }
 
    // �� �� ���� Ȯ��
    boolean unUsedInRow(int i,int num)
    {
        for (int j = 0; j<N; j++)
           if (mat[i][j] == num)
                return false;
        return true;
    }
 
    // �� �� ���� Ȯ��
    boolean unUsedInCol(int j,int num)
    {
        for (int i = 0; i<N; i++)
            if (mat[i][j] == num)
                return false;
        return true;
    }
 
    // �������� ä�� ��� �Լ�
    boolean fillRemaining(int i, int j)
    {
        if (j>=N && i<N-1)
        {
            i = i + 1;
            j = 0;
        }
        if (i>=N && j>=N)
            return true;
 
        if (i < SRN)
        {
            if (j < SRN)
                j = SRN;
        }
        else if (i < N-SRN)
        {
            if (j==(int)(i/SRN)*SRN)
                j =  j + SRN;
        }
        else
        {
            if (j == N-SRN)
            {
                i = i + 1;
                j = 0;
                if (i>=N)
                    return true;
            }
        }
        
        
        for (int num = 1; num<=N; num++)
        {
            if (CheckIfSafe(i, j, num))
            {
                mat[i][j] = num;
                if (fillRemaining(i, j+1))
                    return true;
 
                mat[i][j] = 0;
            }
        }
        
                return false;
    }
 
    
    // ���� �޼ҵ�
    public void removeKDigits()
    {
        int count = K;
        while (count != 0)
        {
            int cellId = randomGenerator(N*N)-1;
 
            int i = (cellId/N);
            int j = cellId%9;
            if (j != 0)
                j = j - 1;
 
            if(Enter_answer[i][j] != 0)
            {
                count--;
                Enter_answer[i][j] = 0;
            }
        }
    }
 	public int[][] pirntSudoku(){
 		for (int i = 0; i<N; i++)
        {
            for (int j = 0; j<N; j++){
                System.out.print(mat[i][j] + " ");
                if(j == 2 || j == 5){
                    System.out.print("| ");
                }
            }
            if(i == 2 || i == 5){
                System.out.print("\n�ѤѤѤѤѤѤѤѤѤѤ�-------");
            }
            System.out.println();
        }
        System.out.println();
        return mat;
 	}
    // ������ ���
    public int[][] quiz_printSudoku()
    {                
        for (int i = 0; i<N; i++)
        {
            for (int j = 0; j<N; j++){
                System.out.print(Enter_answer[i][j] + " ");
                //jb[i][j] = new JButton(""+Enter_answer[i][j]);
                if(j == 2 || j == 5){
                    System.out.print("| ");
                }
            }
            if(i == 2 || i == 5){
                System.out.print("\n�ѤѤѤѤѤѤѤѤѤѤ�-------");
            }
            System.out.println();
        }
        System.out.println();
        return Enter_answer;
    }
    
    
}
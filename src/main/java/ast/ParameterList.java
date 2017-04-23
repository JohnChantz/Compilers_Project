package ast;

import java.util.ArrayList;
import java.util.List;

public class ParameterList extends ASTNode{

    private List<ParameterDeclaration> parameterList;
    
    public ParameterList(){
        this.parameterList = new ArrayList<ParameterDeclaration>();
    }

    public List<ParameterDeclaration> getParameterList() {
        return parameterList;
    }

    public void setParameterList(List<ParameterDeclaration> parameterList) {
        this.parameterList = parameterList;
    }
    
    public void add(ParameterDeclaration parameter){
        this.parameterList.add(parameter);
    }
    
    @Override
    public void accept(ASTVisitor visitor) throws ASTVisitorException {
    }
    
}

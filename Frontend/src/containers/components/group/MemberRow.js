import React from 'react'
import ButtonWrapper from '../ButtonWrapper'

class MemberRow extends React.Component{
    render() {

        const styleObj = {
            borderRadius: '50px',
            border: '7px double #000000',
            fontFamily: 'Georgia, serif',
            fontSize: '16px',
            paddingLeft: '2px',
            paddingRight: '2px',
            paddingTop: '5px',
            marginTop: '4px',
            boxShadow: '5px 5px 10px 0px rgba(0,0,0,0.75)'

        };
        let removeButton = null;
        if (this.props.remove === true && this.props.type !== 1){
            removeButton = <ButtonWrapper 
                                id={this.props.id} 
                               divClass='col-lg-8'
                               class='btn btn-danger' 
                               click={this.props.removeAction} 
                               val={this.props.val} /> ;
        }
        
        
        return (
            <div style={styleObj} className={this.props.type === 1 ? 'row justify-content-between bg-success text-white' : 'row justify-content-between bg-light text-dark'}>
                <div style={{marginLeft: '2%'}}>
                    <h5>{this.props.name}</h5>
                </div>
            <div style={{marginRight: '2%'}}>
                {removeButton}
                </div>
            </div>
        );
    }
}

export default MemberRow;
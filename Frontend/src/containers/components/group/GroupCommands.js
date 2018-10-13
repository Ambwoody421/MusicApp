import React from 'react'

class GroupCommands extends React.Component {

    render() {
        const array = this.props.buttons;
        const elements = [];
        for (var i = 0; i < array.length; i++) {
            const id = this.props.id + ":" + array[i];

            elements.push(<input key={i + ':groupCommands' + this.props.id} type='button' id={id} style={{margin: '7px'}} className={this.props.selected === id ? 'btn-danger btn-sm wrapText' : 'btn-primary btn-sm wrapText'} value={array[i]} onClick={this.props.handleClick} />);
        }
    
        return (
            <div className='row justify-content-center'>
                {elements}
            </div>
        );
    }
}
export default GroupCommands;

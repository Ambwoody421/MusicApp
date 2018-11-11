import React from 'react'
import ButtonWrapper from './ButtonWrapper'
import InputWrapper from './formFields/InputWrapper'
import {status, text} from '../../modules/functions'
import Config from '../../modules/config'

class Login extends React.Component {
    constructor(props){
        super(props);
        this.state = {
            name: '',
            password: '',
            formErrors: {nameError: '', passwordError: ''},
            outcome: ''
        }
        this.handleChange = this.handleChange.bind(this);
        this.submit = this.submit.bind(this);
        this.validateForm = this.validateForm.bind(this);
        this.createUser = this.createUser.bind(this);
    }

    handleChange(e) {
        this.setState({[e.target.id]: e.target.value });
    }

    submit() {
        let obj = {name: this.state.name, password: this.state.password};

        const request = JSON.stringify(obj);


        fetch('http://'+Config.ip+':8080/user/validateUser', {
            credentials: 'include',
            method: 'POST',
            headers: {'Content-Type': 'application/json'},
            body: request
        }).then(status)
            .then(text)
            .then((data) => {
            this.setState({outcome: data});
        })
            .catch(error =>
                   this.setState({outcome: error.message}));

    }

    createUser() {
        let obj = {name: this.state.name, password: this.state.password};

        const request = JSON.stringify(obj);


        fetch('http://'+Config.ip+':8080/user/createUser', {
            credentials: 'include',
            method: 'POST',
            headers: {'Accept': 'text/plain', 'Content-Type': 'application/json'},
            body: request
        }).then(status)
            .then(text)
            .then((data) => {
            
            this.setState({outcome: data});
        })
            .catch(error =>
                   this.setState({outcome: error.message}));
    }

    validateForm(x) {
        let errorCount = 0;
        let nameError = '';
        let passwordError = '';

        if(this.state.name.length === 0){
            nameError = 'Email is required';
            errorCount++;
        }
        if(this.state.password.length === 0){
            passwordError = 'Password is required';
            errorCount++;
        }

        this.setState({formErrors: {nameError: nameError, passwordError: passwordError}});

        if(errorCount === 0){

            if(x === 'new'){
                this.createUser();
            } else {
                this.submit();
            }
        }

    }

    render() {
        return (
            <div className='jumbotron'>
                <h3 className='offset-lg-5 col-lg-2'>Welcome!</h3>
                <div className='row'>
                    <InputWrapper id='name' divClass='col-lg-6 offset-lg-3' displayName='Email' type='text' class='form-control' val={this.state.name} errorText={this.state.formErrors.nameError} handleChange={this.handleChange} />
                </div>
                <div className='row'>
                    <InputWrapper id='password' divClass='col-lg-6 offset-lg-3' displayName='Password' type='password' class='form-control' val={this.state.password} errorText={this.state.formErrors.passwordError} handleChange={this.handleChange} />
                </div>
                <div className='row justify-content-center'>
                    <ButtonWrapper class='btn-primary btn-sm text-center' style={{marginRight: '16px'}} val='Login' click={this.validateForm} />
                    <ButtonWrapper class='btn-danger btn-sm text-center' val='Create' click={() => this.validateForm('new')} />
                </div>
                {this.state.outcome}
            </div>
        );
    }
}
export default Login;
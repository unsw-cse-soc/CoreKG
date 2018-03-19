import React from "react";
import ReactDOM from "react-dom";
import RenderInput from './ui/RenderInput';
import RenderSubmitButton from './ui/RenderSubmitButton';
import RenderDropDown from './ui/RenderDropDown';
import { connect } from 'react-redux';
import { reduxForm, Field, formValueSelector } from 'redux-form';
import { CREATE_CLIENT, CREATE_USER, REQUEST_TOKEN } from '../constants/ActionTypes';
import ResponseComponent from './ResponseComponent';
import isNil from 'lodash/isNil';
import isEmpty from 'lodash/isEmpty';
import trimValue from '../services/TrimValue';

class InstallationComponent extends React.Component {

    constructor(props) {
        super(props);
    }

    componentDidMount() {
    }

    componentDidUpdate(prevProps) {
    }

    render() {
        const { handleCreateClientSubmit, handleCreateUserSubmit, handleLoginSubmit, responses } = this.props;
        return <div class="row">
            <div class="col s12 m12 l12">
                <div class="card-panel teal lighten-2">Step1: Create your client</div>
                <p class="caption">
                    Data API employs OAuth 2.0 method for authorisation. In order to be able to communicate with the API you need to create a client which allows you to authenticate to the system.
                In order to create a client, you need to send a POST request to https:// for example:
                </p>
                <CreateClientForm onSubmit={handleCreateClientSubmit} />
                {responses.has(CREATE_CLIENT) && <ResponseComponent res={responses.get(CREATE_CLIENT)} />}
            </div>
            <div class="col s12 m12 l12">
                <div class="card-panel teal lighten-2">Step2: Create users</div>
                <p class="caption">
                    Once you created a client, you will be able to define one or multiple users. It can be a single admin user or multiple users.
                    Later one you can set permission based on the role of a user. Access level can be applied on action and resource level.
                </p>
                <CreateUserForm onSubmit={handleCreateUserSubmit} />
                {responses.has(CREATE_USER) && <ResponseComponent res={responses.get(CREATE_USER)} />}
            </div>
            <div class="col s12 m12 l12">
                <div class="card-panel teal lighten-2">Step3: Acquire access token</div>
                <p class="caption">
                    Finaly, you will need to obtain an access token which will be included in authorization header of all requests.
                    The token is valid for 15 minutes and you will need to request a new token using the refresh token which be provided with the access token.
                </p>
                <GetTokeForm onSubmit={handleLoginSubmit} />
                {responses.has(REQUEST_TOKEN) && <ResponseComponent res={responses.get(REQUEST_TOKEN)} />}
            </div>
        </div>
    }
}

export default InstallationComponent;

class CreateClientForm extends React.Component {
    constructor(props) {
        super(props);
    }

    render() {
        const {handleSubmit, submitting, clientNameValue} = this.props;
        return <div class="row">
            <div class="col s12 m12 l12">
                <div class="card blue-grey darken-1">
                    <div class="card-content white-text">
                        <pre>{`curl -H "Content-Type: application/json" -X POST -d '{"name":"${(isNil(clientNameValue) || isEmpty(clientNameValue)) ? 'DataLake_NAME' : clientNameValue}"}' http://CoreKG/api/clients`}</pre>
                    </div>
                </div>
            </div>
            <form class="col s12 m12 l12" onSubmit={handleSubmit}>
                <Field name="client"
                    id="client-input"
                    label="Name"
                    component={RenderInput}
                    type="text"
                    normalize={trimValue} />
                <RenderSubmitButton label="Submit" id="client-submit" name="client-submit" />
            </form>
        </div>
    }
}

CreateClientForm = reduxForm({
    form: 'newClientForm',
    validate: values => {
        const errors = {};
        const message = 'This field is mandatory.';
        // if (isNil(values.client)) {
        //     errors.client = message;
        // }
        return errors;
    }
})(CreateClientForm);

const clientFormSelector = formValueSelector('newClientForm');

CreateClientForm = connect(
    state => {
        return {
            clientNameValue: clientFormSelector(state, 'client')
        }
    }
)(CreateClientForm);

class CreateUserForm extends React.Component {
    constructor(props) {
        super(props);
    }

    render() {
        const {handleSubmit, submitting, clientNameValue, clientSecretValue, userNameValue, passwordValue, roleValue} = this.props;

        return <div class="row">
            <div class="col s12 m12 l12">
                <div class="card blue-grey darken-1">
                    <div class="card-content white-text">
                        <pre>{`curl -H "Content-Type: application/json" -X POST -d '{"userName":"${(isNil(userNameValue) || isEmpty(userNameValue)) ? 'USER_NAME' : userNameValue}", "password": "${(isNil(passwordValue) || isEmpty(passwordValue)) ? 'PASSWORD' : passwordValue}", "role":"${(isNil(roleValue) || isEmpty(roleValue)) ? 'ROLE' : roleValue}", "clientName":"${(isNil(clientNameValue) || isEmpty(clientNameValue)) ? 'DataLake_NAME' : clientNameValue}", "clientSecret":"${(isNil(clientSecretValue) || isEmpty(clientSecretValue)) ? 'DataLake_SECRET' : clientSecretValue}"}'http://CoreKG/api/account`}</pre>
                    </div>
                </div>
            </div>
            <form class="col s12 m12 l12" onSubmit={handleSubmit}>
                <Field name="userClientName"
                    id="userClientName-input"
                    label="Client name"
                    component={RenderInput}
                    type="text"
                    normalize={trimValue} />
                <Field name="userClientSecret"
                    id="userClientSecret-input"
                    label="Client secret"
                    normalize={trimValue}
                    component={RenderInput}
                    type="text" />
                <Field name="userName"
                    id="username-input"
                    label="Username"
                    normalize={trimValue}
                    component={RenderInput}
                    type="text" />
                <Field name="password"
                    id="password-input"
                    label="Password"
                    normalize={trimValue}
                    component={RenderInput}
                    type="password" />
                <Field name="role"
                    id="role-input"
                    label="Role"
                    normalize={trimValue}
                    component={RenderInput}
                    type="text" />
                <RenderSubmitButton label="Submit" id="user-submit" name="user-submit" />
            </form>
        </div>
    }
}

CreateUserForm = reduxForm({
    form: 'newUserForm',
    validate: values => {
        const errors = {};
        const message = 'This field is mandatory.';
        return errors;
    }
})(CreateUserForm);

const userFormSelector = formValueSelector('newUserForm');

CreateUserForm = connect(
    state => {
        return {
            clientNameValue: userFormSelector(state, 'userClientName'),
            clientSecretValue: userFormSelector(state, 'userClientSecret'),
            userNameValue: userFormSelector(state, 'userName'),
            passwordValue: userFormSelector(state, 'password'),
            roleValue: userFormSelector(state, 'role')
        }
    }
)(CreateUserForm);

class GetTokeForm extends React.Component {
    constructor(props) {
        super(props);
    }

    render() {
        const {handleSubmit,
            submitting,
            loginClientNameValue,
            loginClientSecretValue,
            loginUserNameValue,
            loginPasswordValue} = this.props;
        return <div class="row">
            <div class="col s12 m12 l12">
                <div class="card blue-grey darken-1">
                    <div class="card-content white-text">
                        <pre>{`curl -H "Content-Type: application/json" -X POST -d '{"userName":"${(isNil(loginUserNameValue) || isEmpty(loginUserNameValue)) ? 'USER_NAME' : loginUserNameValue}", "password": "${(isNil(loginPasswordValue) || isEmpty(loginPasswordValue)) ? 'PASSWORD' : loginPasswordValue}", "grant_type": "PASSWORD","clientName":"${(isNil(loginClientNameValue) || isEmpty(loginClientNameValue)) ? 'YOUR_CLIENT' : loginClientNameValue}", "clientSecret":"${(isNil(loginClientSecretValue) || isEmpty(loginClientSecretValue)) ? 'YOUR_CLIENT_SECRET' : loginClientSecretValue}"}'http://CoreKG/api/oauth`}</pre>
                    </div>
                </div>
            </div>
            <form class="col s12 m12 l12" onSubmit={handleSubmit}>
                <Field name="loginClientName"
                    id="loginClientName-input"
                    label="Client name"
                    normalize={trimValue}
                    component={RenderInput}
                    type="text" />
                <Field name="loginClientSecret"
                    id="loginClientSecret-input"
                    label="Client secret"
                    normalize={trimValue}
                    component={RenderInput}
                    type="text" />
                <Field name="loginUserName"
                    id="login-userName-input"
                    label="Username"
                    normalize={trimValue}
                    component={RenderInput}
                    type="text" />
                <Field name="loginPassword"
                    id="login-password-input"
                    label="Password"
                    normalize={trimValue}
                    component={RenderInput}
                    type="password" />
                <RenderSubmitButton label="Submit" id="login-submit" name="login-submit" />
            </form>
        </div>
    }
}

GetTokeForm = reduxForm({
    form: 'getTokeForm',
    validate: values => {
        const errors = {};
        return errors;
    }
})(GetTokeForm);

const getTokeFormSelector = formValueSelector('getTokeForm');

GetTokeForm = connect(
    state => {
        return {
            loginClientNameValue: getTokeFormSelector(state, 'loginClientName'),
            loginClientSecretValue: getTokeFormSelector(state, 'loginClientSecret'),
            loginUserNameValue: getTokeFormSelector(state, 'loginUserName'),
            loginPasswordValue: getTokeFormSelector(state, 'loginPassword')
        }
    }
)(GetTokeForm);


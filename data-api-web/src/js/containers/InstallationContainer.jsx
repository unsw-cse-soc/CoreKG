import React from 'react';
import InstallationComponent from '../components/InstallationComponent';
import { connect } from 'react-redux';
import { createClient, createUser, requestTokenByPassword, clearResponses } from '../actions/AuthActions';
import { getClients, getResponses } from '../selectors/AuthSelectors';

class InstallationContainer extends React.Component {

    componentWillUnmount() {
        this.props.onClearResponses();
    }

    render() {
        const {responses, onCreateClient, onCreateUser, onRequestToken} = this.props;
        return <InstallationComponent
            responses={responses}
            handleCreateClientSubmit={values => {
                onCreateClient(values.client);
            }}
            handleCreateUserSubmit={values => {
                onCreateUser(values.userName, values.password, values.role, values.userClientName, values.userClientSecret);
            }}
            handleLoginSubmit={values => {
                onRequestToken(values.loginUserName, values.loginPassword, values.loginClientName, values.loginClientSecret);
            }} />
    }
}

const mapDispatchToProps = dispatch => {
    return {
        onCreateClient(name) {
            dispatch(createClient(name));
        },
        onCreateUser(userName, password, role, clientName, clientSecret) {
            dispatch(createUser(userName, password, role, clientName, clientSecret));
        },
        onRequestToken(userName, password, clientName, clientSecret) {
            dispatch(requestTokenByPassword(userName, password, clientName, clientSecret));
        },
        onClearResponses() {
            dispatch(clearResponses());
        }
    };
};

const mapStateToProps = (state) => {
    return {
        responses: getResponses(state)
    };
}

export default connect(mapStateToProps, mapDispatchToProps)(InstallationContainer);

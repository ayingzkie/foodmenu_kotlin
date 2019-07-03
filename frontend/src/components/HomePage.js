import React, { Component } from 'react';

class HomePage extends Component {


    render() {
        console.log(this.props,"props")
        return(
            <div>
                Welcome...{this.props.isAuthenticated?this.props.currentUser.name:null}
            </div>
        )
    }
}

export default HomePage;
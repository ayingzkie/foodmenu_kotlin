import React, { Component } from 'react';
import './App.css';
import {
  Route,
  withRouter,
  Switch,
    Link
} from 'react-router-dom';

import { getCurrentUser } from '../util/APIUtils';
import { ACCESS_TOKEN } from '../constants';
import Login from '../user/login/Login';
import Signup from '../user/signup/Signup';
import Profile from '../user/profile/Profile';
import AppHeader from '../common/AppHeader';
import NotFound from '../common/NotFound';
import LoadingIndicator from '../common/LoadingIndicator';
import PrivateRoute from '../common/PrivateRoute';
import HomePage from '../components/HomePage'
import { Layout, notification, message, Menu,Icon, Avatar, Badge  } from 'antd';
const { Content,Sider} = Layout;
const { SubMenu } = Menu;
class App extends Component {
  constructor(props) {
    super(props);
    this.state = {
      currentUser: null,
      isAuthenticated: false,
      isLoading: false,
      collapsed: false
    }
    this.handleLogout = this.handleLogout.bind(this);
    this.loadCurrentUser = this.loadCurrentUser.bind(this);
    this.handleLogin = this.handleLogin.bind(this);

    notification.config({
      placement: 'topRight',
      top: 70,
      duration: 3,
    });    
  }

  loadCurrentUser() {
    this.setState({
      isLoading: true
    });
    getCurrentUser()
    .then(response => {
      this.setState({
        currentUser: response,
        isAuthenticated: true,
        isLoading: false
      });
    }).catch(error => {
      this.setState({
        isLoading: false
      });  
    });
  }


  componentDidMount() {
    this.loadCurrentUser();
  }

  handleLogout(redirectTo="/", notificationType="success", description="You're successfully logged out.") {
    localStorage.removeItem(ACCESS_TOKEN);

    this.setState({
      currentUser: null,
      isAuthenticated: false
    });

    this.props.history.push(redirectTo);

    message[notificationType](description);
  }

  handleLogin() {
    message.success("You're successfully logged in.")
    this.loadCurrentUser();
    this.props.history.push("/");
  }
  onCollapse = collapsed => {
    console.log(collapsed);
    this.setState({ collapsed });
  };

  render() {
    if(this.state.isLoading) {
      return <LoadingIndicator />
    }

    return (




        <Layout className="app-container">
          <AppHeader isAuthenticated={this.state.isAuthenticated}
                     currentUser={this.state.currentUser}
                     onLogout={this.handleLogout} onCollapsed={this.onCollapse}  collapsed={this.state.collapsed}/>
          <Layout>
            {
              this.state.isAuthenticated?
                  <Sider style={{height:'100vh', borderRight:'1px solid #grey'}} trigger={null} theme={"light"} collapsible collapsed={this.state.collapsed}  width={300}>
                    {/*<div style={{padding:'20px',marginTop:'64px'}}>*/}
                      {/*<Badge count={1}>*/}
                        {/*<Avatar shape="circle" size={"large"} icon={"user"}>*/}
                          {/*{this.state.currentUser?this.state.currentUser.name.charAt(0).toUpperCase():null}*/}
                        {/*</Avatar>*/}
                      {/*</Badge>*/}
                      {/*{this.state.currentUser?*/}
                          {/*<Link className={!this.state.collapsed?"fadein":"fadeout"} to={"/users/"+this.state.currentUser.username} style={{marginLeft:'5px'}}><span >{this.state.currentUser.name}</span></Link>*/}
                          {/*:null}*/}
                    {/*</div>*/}
                    <Menu style={{marginTop:'64px'}} theme={'light'} mode="inline" defaultSelectedKeys={['1']}>
                      <Menu.Item key="1">
                        <Icon type="user" />
                        <span>Profile</span>
                      </Menu.Item>
                    </Menu>
                  </Sider>
                  :
                  null
            }
            <Content className="app-content">
                <Switch>
                  <Route exact path="/"
                         render={(props) => <HomePage isAuthenticated={this.state.isAuthenticated}
                                                      currentUser={this.state.currentUser} handleLogout={this.handleLogout} {...props} />}>
                  </Route>
                  <Route path="/login"
                         render={(props) => <Login onLogin={this.handleLogin} {...props} />}></Route>
                  <Route path="/signup" component={Signup}></Route>
                  <Route path="/users/:username"
                         render={(props) => <Profile isAuthenticated={this.state.isAuthenticated} currentUser={this.state.currentUser} {...props}  />}>
                  </Route>
                  <PrivateRoute authenticated={this.state.isAuthenticated} path="/success" component={HomePage} handleLogout={this.handleLogout}></PrivateRoute>
                  <Route component={NotFound}></Route>
                </Switch>
            </Content>
          </Layout>
        </Layout>
    );
  }
}

export default withRouter(App);

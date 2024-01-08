import { App, Image, Layout, LayoutProps } from "antd"
import { Content, Footer, Header } from "antd/es/layout/layout"
import NavBar from "./navbar"

const CustomLayout = ({ children }: LayoutProps) => {
  return(
    <App>
      <Layout style={{minHeight: '100vh', justifyItems: 'center'}}>
        <Header style={{padding: 0}}>
          <NavBar></NavBar>
        </Header>
        <Content className="site-layout" style={{ padding: '40px 50px' }}>
          {children}
        </Content>
        <Footer style={{ textAlign: 'left', paddingLeft: '5%', backgroundColor: 'white', height: 'auto' }}>
          <Image src="https://i.ibb.co/JmB4xhT/Truckr-Logo.png" height={24} style={{paddingRight: 15}}></Image>© 2023 Truckr, Inc
        </Footer>
      </Layout>
    </App>
  );
}

export default CustomLayout;
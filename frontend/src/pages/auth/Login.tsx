
/**
 * Components
 */
import LoginForm from '@/components/LoginForm';



const Login = () => {
  return (
    <div className="h-dvh flex items-center justify-center p-6 md:p-10">
      <div className="w-full max-w-sm md:max-w-3xl">
        <LoginForm />
      </div>
    </div>
  );
};

export default Login;

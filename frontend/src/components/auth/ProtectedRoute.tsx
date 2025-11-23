/**
 * Node modules
 */
import { Navigate } from 'react-router';

/**
 * Store
 */
import { useAuthStore } from '@/store/auth.store';

interface ProtectedRouteProps {
  children: React.ReactNode;
  roles?: ('AGENTE' | 'AMMINISTRATORE' | 'PROPRIETARIO')[];
}

const ProtectedRoute = ({ children, roles }: ProtectedRouteProps) => {
  const { isAuthenticated, user } = useAuthStore();

  if (!isAuthenticated || !user) return <Navigate to="/login" />;
  if (roles && !roles.includes(user.ruolo)) {
    return <Navigate to="/backoffice/unauthorized" />;
  }
  return <> {children} </>;
};

export default ProtectedRoute;

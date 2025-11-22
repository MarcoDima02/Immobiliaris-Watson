/**
 * Node modules
 */
// import { useNavigate  } from 'react-router';
// import { z } from 'zod';
import { useForm, Controller } from 'react-hook-form';
import { zodResolver } from '@hookform/resolvers/zod';
import { useCallback,  } from 'react';

/**
 * Helpers
 */
import { cn } from '@/lib/utils';

/**
 * Components
 */
import { Button } from '@/components/ui/button';
import { Card, CardContent } from '@/components/ui/card';
import { Input } from '@/components/ui/input';
import {
  Field,
  FieldGroup,
  FieldLabel,
} from '@/components/ui/field';
import InputPassword from '@/components/InputPassword';

/**
 * Assets
 */
import { loginBanner } from '@/assets';
import { logo } from '@/assets';

/**
 * Icons
 */
// import { LoaderCircleIcon } from 'lucide-react';

/**
 * Schemas
 */
import { loginSchema } from '@/schemas/loginSchema';

/**
 * Types
 */
import type { LoginSchemaType } from '@/schemas/loginSchema';

// type LoginFieldName = 'email' | 'password';

/**
 * Constants
 */
const LOGIN_FORM = {
  title: 'Bentornati',
  description: 'Accedi al tuo account personale',
  footerText: 'Hai dimenticato la tua password?',
} as const;

const LoginForm = ({ className, ...props }: React.ComponentProps<'div'>) => {
  // const navigate = useNavigate();

  const { control, handleSubmit } = useForm<LoginSchemaType>({
    resolver: zodResolver(loginSchema),
    defaultValues: {
      email: '',
      password: '',
    },
  });

  const onSubmit = useCallback(async (values: LoginSchemaType) => {
    console.log(values);
  }, []);

  return (
    <div
      className={cn('flex flex-col gap-6', className)}
      {...props}
    >
      <Card className="overflow-hidden p-0">
        <CardContent className="grid p-0 md:grid-cols-2">
          <form
            className="p-6 md:p-8"
            onSubmit={handleSubmit(onSubmit)}
          >
            <div className="flex flex-col gap-6">
              <div className="flex flex-col items-center">
                <figure>
                  <img
                    src={logo}
                    width={75}
                    alt="Logo del sito"
                  />
                </figure>

                <p className="text-balance">{LOGIN_FORM.description}</p>
              </div>
              <FieldGroup>
                <Controller
                  name="email"
                  control={control}
                  render={({ field, fieldState }) => (
                    <Field className="grid gap-3">
                      <FieldLabel>Email</FieldLabel>

                      <Input
                        type="email"
                        {...field}
                        placeholder="Es. esempio@email.com"
                      />
                      {fieldState.error && (
                        <p className="text-red-500 text-sm">
                          {fieldState.error.message}
                        </p>
                      )}
                    </Field>
                  )}
                />

                <Controller
                  name="password"
                  control={control}
                  render={({ field, fieldState }) => (
                    <Field className="grid gap-3">
                      <FieldLabel>Email</FieldLabel>

                      <InputPassword
                        {...field}
                        placeholder="Inserisci la tua password"
                      />
                      {fieldState.error && (
                        <p className="text-red-500 text-sm">
                          {fieldState.error.message}
                        </p>
                      )}
                    </Field>
                  )}
                />
              </FieldGroup>

              <Button
                type="submit"
                className="w-full"
                // disabled={isLoading}
              >
                {/* {isLoading && <LoaderCircleIcon className='animate-spin'/>} */}

                <span>Login</span>
              </Button>
            </div>

            <div className="mt-4 text-center text-sm">
              {LOGIN_FORM.footerText}
            </div>
          </form>

          <figure className=" relative hidden md:block">
            <img
              src={loginBanner}
              alt="Immagine di un appartamento"
              width={400}
              height={400}
              className="absolute inset-0 h-full object-cover"
            />
          </figure>
        </CardContent>
      </Card>
    </div>
  );
};

export default LoginForm;

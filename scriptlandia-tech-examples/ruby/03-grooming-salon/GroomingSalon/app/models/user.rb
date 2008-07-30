class User < ActiveRecord::Base

  belongs_to :company

  validates_presence_of :username
  validates_uniqueness_of :username
  validates_confirmation_of :password

  attr_reader :password

  def password=(pw)
    @password = pw # used by confirmation validator

    salt = [Array.new(6){rand(256).chr}.join].pack("m").chomp # 2^48 combos

    self.password_salt, self.password_hash =
      salt, Digest::MD5.hexdigest(pw + salt)
  end

  def password_is?(pw)
    Digest::MD5.hexdigest(pw + password_salt) == password_hash
    #true
  end

  def current_user(session)
    if session[:user]
      User.find(session[:user])
    end
  end

end
